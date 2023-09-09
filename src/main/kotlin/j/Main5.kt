package j

sealed class Operation {
    abstract fun calculate(n1: Int, n2: Int): Int

    data object Sum : Operation() {
        override fun calculate(n1: Int, n2: Int): Int = n1 + n2
    }

    data object Subtract : Operation() {
        override fun calculate(n1: Int, n2: Int): Int = n1 - n2
    }

    data object Multiply : Operation() {
        override fun calculate(n1: Int, n2: Int): Int = n1 * n2
    }

    data object Divide : Operation() {
        override fun calculate(n1: Int, n2: Int): Int = n1 / n2
    }
}

class Calculator {
    fun evaluate(n1: Int, n2: Int, operation: Operation): Int = operation.calculate(n1, n2)
}