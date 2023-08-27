package g

import kotlinx.coroutines.*

fun main() = runBlocking {
    val action = async {
        delay(1000)
        println("World!")
        return@async "hallo"
    }
    print("Hello ")

    println(action.await())
}

fun main2() {
    Main3.Person("ee", 25, Main3.Gender.MALE).applyCustom {
        println(name)
    }
}

inline fun <T> T.applyCustom(action: T.() -> Unit) {
    action(this)
}