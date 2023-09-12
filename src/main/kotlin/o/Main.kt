package o

import kotlin.reflect.KProperty

fun main() {
    val point = Point3D()
    point.x = 4
    point.y = -4
    point.z = -41
    println(point)
}

class Point3D {
    private val values = intArrayOf(0, 0, 0)

    var x: Int by arrayAccess()
    var y: Int by arrayAccess()
    var z: Int by arrayAccess()

    private fun arrayAccess(): ArrayAccess = ArrayAccess(values)

    override fun toString(): String {
        return "Point3D(${values.joinToString()})"
    }
}

private class ArrayAccess(private val array: IntArray) {
    operator fun getValue(point3D: Point3D, property: KProperty<*>): Int {
        println("getValue of ${property.name} on $point3D")
        return array[getIndex(property)]
    }

    operator fun setValue(point3D: Point3D, property: KProperty<*>, value: Int) {
        println("setValue of ${property.name} to $value on $point3D")
        array[getIndex(property)] = value
    }

    private fun getIndex(property: KProperty<*>): Int {
        return when (property.name) {
            "x" -> 0
            "y" -> 1
            "z" -> 2
            else -> throw IllegalArgumentException("Invalid property")
        }
    }
}