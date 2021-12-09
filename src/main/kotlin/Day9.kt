class Day9(input: List<String>) {

    private val points = input
        .map { line -> line.toCharArray().map { value -> value.toString().toInt() } }
        .flatMapIndexed { y, values -> values.mapIndexed { x, value -> Point(x, y) to value } }
        .toMap()

    fun part1() = lowPoints().values.sumOf { value -> value + 1 }

    fun part2() = lowPoints().keys
        .map { lowPoint -> groupBasin(setOf(lowPoint), listOf(lowPoint)).size }
        .sorted().reversed().take(3).reduce { current, next -> current * next }

    private fun lowPoints() = points.filter { (point, value) ->
        directions.all { direction -> (points[point + direction] ?: 9) > value }
    }

    private fun groupBasin(basin: Set<Point>, pointsForCheck: List<Point>): Set<Point> {
        return if (pointsForCheck.isEmpty()) basin
        else {
            val nextPoints = pointsForCheck.flatMap { current ->
                directions.map { direction -> current + direction }
                    .filter { neighbour -> neighbour !in basin }
                    .filter { neighbour -> (points[neighbour] ?: 9) < 9 }
            }
            groupBasin(basin + nextPoints, nextPoints)
        }
    }

    private val directions = listOf(Point(-1, 0), Point(1, 0), Point(0, -1), Point(0, 1))

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    }
}
