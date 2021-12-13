import kotlin.math.abs

class Day13(input: List<String>) {
    private val points = input.filter { line -> line.matches(Regex("""\d+,\d+""")) }
        .map { line -> line.split(",").map { value -> value.toInt() } }
        .map { (x, y) -> Point(x, y) }.toSet()

    private val folds = input.filter { line -> line.contains("fold along") }
        .map { line -> line.replace("fold along ", "") }
        .map { line -> line.split("=") }
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
        val xMax = state.maxOf { point -> point.x }
        val xMin = state.minOf { point -> point.x }
        val yMax = state.maxOf { point -> point.y }
        val yMin = state.minOf { point -> point.y }

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
