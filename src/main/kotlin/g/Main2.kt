package g

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.CyclicBarrier
import java.util.concurrent.Executors
import java.util.concurrent.locks.Lock
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.coroutines.ContinuationInterceptor

suspend fun main(): Unit = coroutineScope {
//    val lock1 = ReentrantLock()
//    val lock2 = ReentrantLock()

//    val executor = Executors.newFixedThreadPool(2)
//
//    executor.submit {
//        println("Starting 1")
//        lock1.withLock {
//            Thread.sleep(200)
//            lock2.withLock {
//                Thread.sleep(200)
//                println("1")
//            }
//        }
//    }
//
//    executor.submit {
//        println("Starting 2")
//        lock2.withLock {
//            lock1.withLock {
//                println("2")
//            }
//        }
//    }
//
//    executor.shutdown()
//
//    return@coroutineScope

    val mutex1 = Mutex()
    val mutex2 = Mutex()

    val barrier = CyclicBarrier(2)

    val tasks: MutableList<Job> = mutableListOf()

    tasks += launch {
        println("1 " + coroutineContext[ContinuationInterceptor])

        barrier.await()
        println("Starting 1")
        mutex1.withLock {
            delay(100)
            mutex2.withLock {
                delay(100)
                println("1")
            }
        }
    }

    tasks += launch {
        println("2 " + coroutineContext[ContinuationInterceptor])

        barrier.await()
        println("Starting 2")
        mutex2.withLock {
            delay(100)
            mutex1.withLock {
                delay(100)
                println("2")
            }
        }
    }

    tasks.forEach { it.join() }
}

inline fun Lock.withLock1(action: () -> Unit) {
    lock()
    try {
        action()
    } finally {
        unlock()
    }
}

@OptIn(ExperimentalContracts::class)
inline fun <T> Lock.withLock2(action: () -> T): T {
    contract { callsInPlace(action, InvocationKind.EXACTLY_ONCE) }
    lock()
    try {
        return action()
    } finally {
        unlock()
    }
}