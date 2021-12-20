import kotlin.math.pow

class Day20(val input: List<String>) {

    private val algorithm = input.first().toCharArray()

    private val initialImage = input.drop(2)
        .flatMapIndexed { y, line -> line.mapIndexed { x, symbol -> Point(x, y) to symbol } }.toMap()

    fun part1() = solve(2)

    fun part2() = solve(50)

    private fun solve(steps: Int) = (0 until steps).fold(initialImage) { image, index -> step(image, index) }
        .filterValues { value -> value == '#' }.count()

    private fun step(image: Map<Point, Char>, index: Int): Map<Point, Char> {
        val surroundedBy = if (algorithm[0] == '.' || index % 2 == 0) '.' else '#'

        val minX = image.keys.minOf { point -> point.x } - 1
        val maxX = image.keys.maxOf { point -> point.x } + 1
        val minY = image.keys.minOf { point -> point.y } - 1
        val maxY = image.keys.maxOf { point -> point.y } + 1

        return (minX..maxX).flatMap { x -> (minY..maxY).map { y -> Point(x, y) } }.associateWith { point ->
            val value = template.map { (direction, value) -> (point + direction) to value }
                .sumOf { (point, value) -> if ((image[point] ?: surroundedBy) == '#') value else 0 }

            algorithm[value]
        }
    }

    private val template = (-1..1).flatMap { y -> (-1..1).map { x -> Point(x, y) } }.reversed()
        .mapIndexed { index, point -> point to 2.0.pow(index.toDouble()).toInt() }.toMap()

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    }
}