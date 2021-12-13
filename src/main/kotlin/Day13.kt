import kotlin.math.abs

class Day13(input: List<String>) {
    private val points = input.filter { it.matches(Regex("""\d+,\d+""")) }
        .map { it.split(",").map { value -> value.toInt() } }
        .map { (x, y) -> Point(x, y) }.toSet()

    private val folds = input.filter { it.contains("fold along") }
        .map { it.replace("fold along ", "") }
        .map { it.split("=") }
        .map { (diagonal, value) -> Fold(Diagonal.valueOf(diagonal.uppercase()), value.toInt()) }

    fun part1() = foldPoints(points, folds.first()).size

    fun part2() = printPoints(folds.fold(points) { state, fold -> foldPoints(state, fold) })

    private fun foldPoints(points: Set<Point>, instruction: Fold) = points.map { (x, y) ->
        when (instruction.diagonal) {
            Diagonal.X -> Point(instruction.value - abs(x - instruction.value), y)
            Diagonal.Y -> Point(x, instruction.value - abs(y - instruction.value))
        }
    }.toSet()

    private fun printPoints(state: Set<Point>): String {
        val xMax = state.maxOf { it.x }
        val xMin = state.minOf { it.x }
        val yMax = state.maxOf { it.y }
        val yMin = state.minOf { it.y }

        return (yMin..yMax).joinToString("\n") { y ->
            (xMin..xMax).joinToString("") { x ->
                if (Point(x, y) in state) "#" else " "
            }
        }
    }

    data class Point(val x: Int, val y: Int)
    enum class Diagonal { X, Y }
    data class Fold(val diagonal: Diagonal, val value: Int)
}
