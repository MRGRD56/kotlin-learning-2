package n

import java.time.LocalDateTime

fun main() {
    val date1: LocalDateTime = LocalDateTime.parse("2023-01-01T01:21:46")
    val date2: LocalDateTime = LocalDateTime.parse("2023-02-01T15:09:51")

    println(date1 < date2)

    val int1 = IntRef(20)
    val int2 = IntRef(25)
    val int3 = IntRef(25)

    println(int1 >= int2)
    println(int1.compareTo(int2) >= 0)
    println(int2 == int3)
}

class IntRef(val value: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as IntRef

        return value == other.value
    }

    override fun hashCode(): Int {
        return 31 * value.hashCode()
    }
}

class Ref<T>(val value: T) : Comparable<Ref<T>> {
    @Throws(ClassCastException::class)
    override fun compareTo(other: Ref<T>): Int {
        return compareValues(value as Comparable<*>, other.value as Comparable<*>)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Ref<*>

        return value == other.value
    }

    override fun hashCode(): Int {
        return value?.hashCode() ?: 0
    }
}

operator fun IntRef.compareTo(other: IntRef) = compareValues(value, other.value)