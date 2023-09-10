package n

import java.util.*
import kotlin.math.pow
import kotlin.time.measureTime

val typesList = listOf(Item.ONE, Item.TWO, Item.THREE) // 775.984700ms
val typesSet = HashSet(typesList) // 983.412600ms
val typesEnumSet: Set<Item> = EnumSet.of(Item.ONE, Item.TWO, Item.THREE) // 242.8ms
val typesLinkedList = LinkedList(typesList) // 1.024878300s
val typesPriorityQueue = PriorityQueue(typesList) // 778.700300ms
val typesTreeSet = TreeSet(typesList) // 919.227800ms

fun main() {
    val types = Array<Item>(500_000_000) { _ -> Item.THREE }

    measureTime {
        types.forEach {
            typesEnumSet.contains(it)
        }
    }.let { println("Completed in $it") }

    val squares = IntArray(10) { (it + 1).let { n -> n * n } }
    println(squares.contentToString())
}

infix fun Int.pow(power: Int): Int = this.toDouble().pow(power).toInt()

enum class Item {
    ONE,
    TWO,
    THREE
}