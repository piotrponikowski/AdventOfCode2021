import kotlin.math.max

class Day17(val input: String) {

    private val target = Regex("""^target area: x=(-?\d+)\.\.(-?\d+), y=(-?\d+)\.\.(-?\d+)$""")
        .matchEntire(input)!!.destructured
        .let { (x1, x2, y1, y2) -> Point(x1.toInt(), y1.toInt()) to Point(x2.toInt(), y2.toInt()) }

    fun part1() = 1
    fun part2() = 2

    data class Probe(val velocity: Point, val maxY: Int, val position: Point = Point(0, 0)) {
        fun step() {
            val newPosition = position + velocity
            val newVelocity = Point(max(velocity.x - 1, 0), velocity.y - 1)
            val maxY = max(newPosition.y, maxY)
            
            Probe(newPosition, maxY, newVelocity)
        }

//        fun missedTarget() =

    }

    private fun isWithinTarget(position: Point) = target
        .let { (a1, a2) -> position.x >= a1.x && position.x <= a2.x && position.y >= a1.y && position.y <= a2.y }

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    }
}

fun main() {
    //2364
    //4716
    val input = readText("day17.txt", false)
//    println(Day17(input).testVelocityY(0, listOf(0, 1, 2, 3, 4, 5, 6, 7)))
    println(Day17(input).part2())
//    println(Day17(input).testVelocityXY(30, -10))

}