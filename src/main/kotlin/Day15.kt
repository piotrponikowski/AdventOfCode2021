import java.util.*

class Day15(val input: List<String>) {

    fun part1() = solve(parseCave())
    fun part2() = solve(createBigCave())

    private val caveWidth = input.first().length
    private val caveHeight = input.size

    private fun parseCave() = input.map { line -> line.map { value -> value.toString().toInt() } }
        .flatMapIndexed { y, line -> line.mapIndexed { x, risk -> Point(x, y) to risk } }.toMap()

    private fun createBigCave() = parseCave()
        .let { cave -> (0 until 5).flatMap { ty -> (0 until 5).flatMap { tx -> copyCave(cave, tx, ty) } }.toMap() }

    private fun copyCave(cave: Map<Point, Int>, tx: Int, ty: Int) = cave.map { (point, risk) ->
        val newPoint = Point(point.x + (tx * caveWidth), point.y + (ty * caveHeight))
        val newRisk = (risk + tx + ty).let { newRisk -> if (newRisk > 9) newRisk - 9 else newRisk }
        newPoint to newRisk
    }

    fun solve(cave: Map<Point, Int>): Int {
        val startPoint = Point(0, 0)
        val endPoint = cave.keys.maxByOrNull { point -> point.x + point.y }!!

        val startRisk = cave[startPoint]!!
        val distanceMap = mutableMapOf(startPoint to startRisk)

        val searchQueue = PriorityQueue<Point> { point1, point2 -> distanceMap[point1]!! - distanceMap[point2]!! }
        searchQueue.add(startPoint)

        while (searchQueue.isNotEmpty()) {
            val point = searchQueue.poll()
            val risk = distanceMap[point]!!

            val neighbours = directions
                .map { direction -> point + direction }
                .filter { neighbour -> neighbour in cave }

            neighbours.forEach { neighbour ->
                val newDistance = risk + cave[neighbour]!!
                val prevDistance = distanceMap[neighbour] ?: Int.MAX_VALUE

                if (newDistance < prevDistance) {
                    distanceMap[neighbour] = newDistance
                    searchQueue += neighbour
                }
            }
        }

        return distanceMap[endPoint]!! - startRisk
    }

    private val directions = listOf(Point(-1, 0), Point(1, 0), Point(0, -1), Point(0, 1))

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    }
}