import java.util.*
import java.util.concurrent.ExecutorService
import java.util.function.Consumer

class TaskInvoker<T>(private val executor: ExecutorService) {
    private val tasks: MutableList<T> = Collections.synchronizedList(mutableListOf())

    fun submit(task: () -> T): Unit {
        TODO()
    }

//    fun submit(task: () -> Unit): Unit {
//        TODO()
//    }

    fun submit(task: MultiConsumer<T>): Unit {
        TODO()
    }

    fun submit(task: MultiConsumer<T>, listFactory: () -> List<T>) {
        TODO()
    }

//    fun submitAll(tasks: Collection<() -> T>) {
//        TODO()
//    }

//    @JvmName("submitAllVoid")
    fun submitAll(tasks: Collection<() -> Unit>) {

    }

    interface MultiConsumer<T> : Consumer<T> {
        override fun accept(t: T)

        fun acceptAll(items: Collection<T>)
    }
}