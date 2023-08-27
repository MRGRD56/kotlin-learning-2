package d

fun main() {
    val range: IntProgression = 100 downTo 1 step 4
    range.asIterable()

    for (i in range) {
        println("Hello $i")
    }

    val array = IntArray(1000) { (-10000..10000).random() }
    bubbleSort(array)
    println(array.joinToString(prefix = "[", postfix = "]"))

    println("Same ${array.contentEquals(array.sortedArray())}")
}

fun bubbleSort(array: IntArray) {
    for (i in 0 ..< array.size - 1) {
        for (j in 0 ..< array.size - 1 - i) {
            if (array[j] > array[j + 1]) {
                array[j] = array[j + 1].also { array[j + 1] = array[j] }
            }
        }
    }
}