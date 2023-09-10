package n

fun main() {
    val point2d = Point2D(0.4, 2.4)
    point2d['x'] = 0.5

    println(point2d)

    val point3d = Point3D(0.4, 2.4, -14.0)
    point3d['x'] = 0.5
    point3d['z'] = -0.5

    println(point3d)
}

interface AbstractPoint {
    operator fun get(key: Char): Double
    operator fun set(key: Char, value: Double)
}

class Point2D(x: Double, y: Double) : AbstractPoint {
    private val xy = DoubleArray(2)

    init {
        xy[0] = x
        xy[1] = y
    }

    override fun get(key: Char): Double = xy[index(key)]
    override fun set(key: Char, value: Double) = run { xy[index(key)] = value }

    private fun index(key: Char): Int = when (key) {
        'x' -> 0
        'y' -> 1
        else -> throw IllegalArgumentException("Invalid key '$key'")
    }

    override fun toString(): String {
        return "Point2D(${xy[0]}, ${xy[1]})"
    }
}

class Point3D(x: Double, y: Double, z: Double) : AbstractPoint {
    private val xy = DoubleArray(3)

    init {
        xy[0] = x
        xy[1] = y
        xy[2] = y
    }

    override fun get(key: Char): Double = xy[index(key)]
    override fun set(key: Char, value: Double) = run { xy[index(key)] = value }

    private fun index(key: Char): Int = when (key) {
        'x' -> 0
        'y' -> 1
        'z' -> 2
        else -> throw IllegalArgumentException("Invalid key '$key'")
    }

    override fun toString(): String {
        return "Point3D(${xy[0]}, ${xy[1]}, ${xy[2]})"
    }
}