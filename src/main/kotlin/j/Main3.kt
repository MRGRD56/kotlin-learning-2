package j

fun main() {
    val (one, two, three) = object {
        operator fun component1(): Int = 1
        operator fun component2(): Int = 2
        operator fun component3(): String = "thr33"
    }

    println("""$one $two ${"\$three"}""")
}