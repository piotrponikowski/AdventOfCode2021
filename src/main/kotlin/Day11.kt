class Day11(input: List<String>) {

    private val data = input.map { line -> line.toCharArray().map { digit -> digit.toString().toInt() } }
        .flatMapIndexed { y, line -> line.mapIndexed { x, energy -> Point(x, y) to energy } }.toMap()

    fun part1() = stepSequence.take(100).last().let { (totalFlashes) -> totalFlashes }

    fun part2() = stepSequence.takeWhile { (_, allFlashed) -> !allFlashed }.count() + 1

    private val stepSequence = sequence {
        var state = data
        var totalFlashes = 0

        while (true) {
            state = step(state)
            totalFlashes += state.filterValues { energy -> energy == 0 }.count()
            val allFlashed = state.all { (_, energy) -> energy == 0 }

            yield(totalFlashes to allFlashed)
        }
    }

    private fun step(state: Map<Point, Int>) = state
        .mapValues { (_, energy) -> energy + 1 }
        .let { chargedState -> flash(chargedState) }
        .mapValues { (_, energy) -> if (energy > 9) 0 else energy }

    private fun flash(state: Map<Point, Int>, flashed: Set<Point> = emptySet()): Map<Point, Int> {
        val flashingPoints = state.filter { (_, energy) -> energy > 9 }.keys - flashed

        if (flashingPoints.isEmpty()) return state

        val chargedPoints = flashingPoints
            .flatMap { point -> directions.map { direction -> point + direction } }
            .groupingBy { point -> point }.eachCount()

        val newState = state
            .map { (point, energy) -> point to energy + (chargedPoints[point] ?: 0) }
            .toMap()

        return flash(newState, flashed + flashingPoints)
    }

    private val directions = (-1..1).flatMap { y -> (-1..1).map { x -> Point(x, y) } }
        .filterNot { point -> point.x == 0 && point.y == 0 }

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    }
}

fun main() {
    val input = readLines("day11.txt", false)
    println(Day11(input).part1())
}