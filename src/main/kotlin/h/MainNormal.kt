package h

import java.lang.IllegalStateException
import java.util.function.Function
import java.util.stream.Collectors
import java.util.stream.IntStream

fun main() {
    val numberMap: Map<Int, String> =
        (0..30).asSequence()
            .filter { it % 2 == 0 }
            .map { it * 10 }
            .associateWith { it.toString(16) }

    println("[${numberMap.javaClass.name}] $numberMap")

    // an alternative way

    val numberMapClassical = LinkedHashMap<Int, String>()
    for (i in 0..30) {
        if (i % 2 == 0) {
            val value = i * 10
            numberMapClassical[value] = value.toString(16)
        }
    }

    println("\nClassic")
    println("[${numberMapClassical.javaClass.name}] $numberMapClassical")

    // java style

    val numberMapJava = IntStream.range(0, 30 + 1)
        .filter { it % 2 == 0 }
        .map { it * 10 }
        .boxed()
        .collect(Collectors.toMap(
            Function.identity(),
            { it.toString(16) },
            { _, _ -> throw IllegalStateException() },
            ::LinkedHashMap
        ))

    println("\nJava")
    println("[${numberMapJava.javaClass.name}] $numberMapJava")
}