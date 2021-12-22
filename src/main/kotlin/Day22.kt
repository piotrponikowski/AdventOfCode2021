import kotlin.math.max
import kotlin.math.min

class Day22(val input: List<String>) {
    private val pattern = Regex("""^(\w+) x=(-?\d+)\.\.(-?\d+),y=(-?\d+)\.\.(-?\d+),z=(-?\d+)\.\.(-?\d+)${'$'}""")

    private val cubes = input.map { line -> pattern.matchEntire(line)!!.destructured }
        .map { (type, x1, x2, y1, y2, z1, z2) ->
            val min = Point(x1.toInt(), y1.toInt(), z1.toInt())
            val max = Point(x2.toInt(), y2.toInt(), z2.toInt())
            Cube(Type.valueOf(type.uppercase()), min, max)
        }

    fun part1() = solve(cubes.filter { cube -> cube.isInitRegion() })

    fun part2() = solve(cubes)

    fun solve(cubes: List<Cube>) = cubes
        .fold(emptyList<Cube>()) { result, cube -> result + step(cube, result) }
        .sumOf { cube -> cube.size() }

    private fun step(cube: Cube, others: List<Cube>): List<Cube> {
        val negatedRegion = others
            .filter { other -> other.intersects(cube) }
            .map { other -> other.intersection(cube) }

        return when (cube.type) {
            Type.ON -> negatedRegion + cube
            Type.OFF -> negatedRegion
        }
    }

    data class Cube(val type: Type, val min: Point, val max: Point) {
        fun intersects(other: Cube): Boolean {
            val conditionX = min.x > other.max.x || max.x < other.min.x
            val conditionY = min.y > other.max.y || max.y < other.min.y
            val conditionZ = min.z > other.max.z || max.z < other.min.z
            return !(conditionX || conditionY || conditionZ)
        }

        fun intersection(other: Cube): Cube {
            val min = Point(max(min.x, other.min.x), max(min.y, other.min.y), max(min.z, other.min.z))
            val max = Point(min(max.x, other.max.x), min(max.y, other.max.y), min(max.z, other.max.z))
            return Cube(!type, min, max)
        }

        fun size() = (max.x - min.x + 1L) * (max.y - min.y + 1L) * (max.z - min.z + 1L) * type.multiplier

        fun isInitRegion() = min.x >= -50 && max.x <= 50
    }

    enum class Type(val multiplier: Int) {
        ON(1), OFF(-1);

        operator fun not() = if (this == ON) OFF else ON
    }

    data class Point(val x: Int, val y: Int, val z: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y, z + other.z)
        operator fun minus(other: Point) = Point(x - other.x, y - other.y, z - other.z)
    }
}