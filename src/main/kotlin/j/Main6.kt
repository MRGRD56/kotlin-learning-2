package j

enum class Operator(operation: Operation) {
    SUM(Operation.Sum) {
        override fun toString(): String {
            return "SUMMA!!!!"
        }
    },
    SUBTRACT(Operation.Subtract),
    MULTIPLY(Operation.Multiply),
    DIVIDE(Operation.Divide)
}

fun main() {
    Operator.entries.forEach { println(it) }
}