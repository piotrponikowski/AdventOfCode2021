import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Day5(private val input: List<String>) {

    fun part1() = solve(false)
    fun part2() = solve(true)

    private fun solve(supportsDiagonal: Boolean) = collectLinePoints(supportsDiagonal)
        .groupingBy { point -> point }.eachCount()
        .filterValues { count -> count > 1 }.count()

    private fun collectLinePoints(supportsDiagonal: Boolean) = parsePoints().flatMap { (a, b) ->
        when {
            a.x == b.x -> toHorizontal(a, b)
            a.y == b.y -> toVertical(a, b)
            supportsDiagonal -> toDiagonal(a, b)
            else -> emptyList()
        }
    }

    private fun parsePoints() = input
        .map { row -> row.split(" -> ").map { element -> element.split(",") } }
        .map { points -> points.map { (x, y) -> Point(x.toInt(), y.toInt()) } }
        .map { (a, b) -> a to b }

    private fun toHorizontal(a: Point, b: Point) = (min(a.y, b.y)..max(a.y, b.y)).map { y -> Point(a.x, y) }

    private fun toVertical(a: Point, b: Point) = (min(a.x, b.x)..max(a.x, b.x)).map { x -> Point(x, a.y) }

    private fun toDiagonal(a: Point, b: Point) = let {
        val steps = abs(a.x - b.x)
        val dx = (b.x - a.x) / steps
        val dy = (b.y - a.y) / steps
        (0..steps).map { di -> Point(a.x + dx * di, a.y + dy * di) }
    }

    data class Point(val x: Int, val y: Int)
}