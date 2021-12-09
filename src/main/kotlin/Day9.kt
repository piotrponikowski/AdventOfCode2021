class Day9(input: List<String>) {

    private val points = input
        .map { line -> line.toCharArray().map { value -> value.toString().toInt() } }
        .flatMapIndexed { y, values -> values.mapIndexed { x, value -> Point(x, y) to value } }
        .toMap()

    fun part1() = lowPoints().values.sumOf { value -> value + 1 }

    fun part2() = lowPoints().keys.map { lowPoint ->
        val pointsForCheck = mutableListOf(lowPoint)
        val basin = mutableSetOf(lowPoint)

        while (pointsForCheck.isNotEmpty()) {
            val current = pointsForCheck.removeFirst()
            val currentValue = points[current]!!

            neighbours.map { neighbour -> current + neighbour }
                .map { other -> other to (points[other] ?: 9) }
                .filter { (other, otherValue) -> otherValue in currentValue..8 && other !in basin }
                .forEach { (other, _) ->
                    pointsForCheck += other
                    basin += other
                }
        }
        basin.size
    }.sorted().reversed().take(3).reduce { a, b -> a * b }

    private fun lowPoints() =
        points.filter { (point, value) -> neighbours.all { neighbour -> (points[point + neighbour] ?: 9) > value } }


    private val neighbours = listOf(
        Point(-1, 0),
        Point(1, 0),
        Point(0, -1),
        Point(0, 1)
    )

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    }
}
