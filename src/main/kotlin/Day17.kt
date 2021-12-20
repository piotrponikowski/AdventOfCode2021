import kotlin.math.max

class Day17(val input: String) {

    private val target = Regex("""^target area: x=(-?\d+)\.\.(-?\d+), y=(-?\d+)\.\.(-?\d+)$""")
        .matchEntire(input)!!.destructured
        .let { (x1, x2, y1, y2) -> Point(x1.toInt(), y1.toInt()) to Point(x2.toInt(), y2.toInt()) }

    fun part1() = solve().maxOf { probe -> probe.maxY }
    fun part2() = solve().count()

    private fun solve() = target
        .let { (t1, t2) -> (0..t2.x).flatMap { vx -> (t1.y..-t1.y).map { vy -> Point(vx, vy) } } }
        .map { velocity -> simulateProbe(velocity) }
        .filter { probe -> probe.isWithinTarget(target) }

    private fun simulateProbe(velocity: Point): Probe {
        var probe = Probe(velocity)
        while (probe.shouldContinue(target)) {
            probe = probe.step()
        }
        return probe
    }

    data class Probe(val velocity: Point, val maxY: Int = 0, val position: Point = Point(0, 0)) {
        fun step(): Probe {
            val newPosition = position + velocity
            val newVelocity = Point(max(velocity.x - 1, 0), velocity.y - 1)
            val maxY = max(newPosition.y, maxY)

            return Probe(newVelocity, maxY, newPosition)
        }

        fun shouldContinue(target: Pair<Point, Point>) = target.let { (t1, t2) ->
            when {
                isWithinTarget(target) -> false
                position.y < t1.y && velocity.y < 0 -> false
                position.x > t2.x && velocity.x > 0 -> false
                position.x < t1.x && velocity.x < 0 -> false
                else -> true
            }
        }

        fun isWithinTarget(target: Pair<Point, Point>) = target.let { (a1, a2) ->
            position.x >= a1.x && position.x <= a2.x && position.y >= a1.y && position.y <= a2.y
        }
    }

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    }
}