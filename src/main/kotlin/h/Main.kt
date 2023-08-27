package h

fun main() {
    val range = 0..30
    val iterator: Iterator<Int> = range.iterator()

    WHILE (iterator::hasNext) {
        iterator.next().let {
            IF ((it % 2) == 0) {
                print("${it * 10} ")
            }
        }
    }
}

inline fun IF(condition: Boolean, action: () -> Unit) {
    if (condition) action()
}

inline fun WHILE(condition: () -> Boolean, action: () -> Unit) {
    while (condition()) action()
}