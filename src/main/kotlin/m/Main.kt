package m

import java.lang.Exception
import java.util.*

fun main() {
//    val string = StringBuilder().build {
    val string = buildString {
        append('H')
        append('e')
        append('l')
        append('l')
        append('o')
        append('!')
        appendLine()
        append("My name is ")
        ('A'..'F').forEach(::append)
        appendLine()
    }

    print(string)

    println(('A'..'Z').joinToString(""))

    val value: String? = null

    try {
        value!!
        println(value)
    } catch (e: NullPointerException) {
        System.err.print("NPE!!! ")
        e.printStackTrace()
    }
}

inline fun StringBuilder.build(action: StringBuilder.() -> Unit): String {
    action(this)
    return this.toString()
}