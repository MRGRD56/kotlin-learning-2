package n

import java.util.*
import kotlin.time.measureTime

fun main() {
    // ListMap - 653.280500ms
    // HashMap - 282.145300ms
    // LinkedHashMap - 311.874700ms

    val map = ListMap<String, Any>().apply {
        this["1"] = "one"
        this["2"] = "tw0"
        this["3"] = "three"
        this["4"] = "four"
        this["2"] = "two"
    }

    println("contains 9 ${"9" in map}")
    println("contains 2 ${"2" in map}")

    measureTime {
        repeat(50_000_000) {
            map["4"] = "uwu"
        }
    }.let(::println)

    println(map)
}

//operator fun <K, V, M : Map<K, V>> M.contains(key: K): Boolean {
//    return this.containsKey(key)
//}

class ListMap<K, V>(
    private val elements: MutableList<MutableMap.MutableEntry<K, V>> = LinkedList<MutableMap.MutableEntry<K, V>>()
) : MutableMap<K, V> {
    override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
        get() = LinkedHashSet(elements)
    override val keys: MutableSet<K>
        get() = elements.mapTo(linkedSetOf()) { it.key }
    override val size: Int
        get() = elements.size
    override val values: MutableCollection<V>
        get() = elements.mapTo(mutableListOf()) { it.value }

    override fun clear() {
        elements.clear()
    }

    override fun isEmpty(): Boolean {
        return elements.isEmpty()
    }

    override fun remove(key: K): V? {
        val iterator = elements.listIterator()
        while (iterator.hasNext()) {
            iterator.next().let {
                if (it.key == key) {
                    iterator.remove()
                    return it.value
                }
            }
        }

        return null
    }

    override fun putAll(from: Map<out K, V>) {
        from.entries.forEach { (key, value) ->
            put(key, value)
        }
    }

    override fun put(key: K, value: V): V? {
        val iterator = elements.listIterator()
        while (iterator.hasNext()) {
            iterator.next().let {
                if (it.key == key) {
                    return it.setValue(value)
                }
            }
        }

        elements.add(mutableEntry(key, value))
        return null
    }

    override fun get(key: K): V? {
        return elements.find { it.key == key }?.value
    }

    override fun containsValue(value: V): Boolean {
        return elements.any { it.value == value }
    }

    override fun containsKey(key: K): Boolean {
        return elements.any { it.key == key }
    }

    override fun toString(): String {
        val i: Iterator<Map.Entry<K, V>> = entries.iterator()
        if (!i.hasNext()) return "{}"
        val sb = StringBuilder()
        sb.append('{')
        while (true) {
            val (key, value) = i.next()
            sb.append(if (key === this) "(this Map)" else key)
            sb.append('=')
            sb.append(if (value === this) "(this Map)" else value)
            if (!i.hasNext()) return sb.append('}').toString()
            sb.append(',').append(' ')
        }
    }
}

private fun <K, V> mutableEntry(key: K, value: V): MutableMap.MutableEntry<K, V> = MutableEntryImpl(key, value)

private class MutableEntryImpl<K, V>(override val key: K, private var _value: V) : MutableMap.MutableEntry<K, V> {
    override val value: V
        get() = _value

    override fun setValue(newValue: V): V {
        return _value.also { _value = newValue }
    }
}