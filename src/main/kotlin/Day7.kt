import kotlin.math.abs

class Day7(private val input: String) {

    fun part1() = solve(::constantCost)
    fun part2() = solve(::increasedCost)

    fun solve(costMethod: (Int) -> Int) = parsePositions().let { positions ->
        val minPosition = positions.minOrNull()!!
        val maxPosition = positions.maxOrNull()!!
        val positionRange = (minPosition..maxPosition)

        positionRange.minOf { position -> positions.sumOf { other -> costMethod(abs(other - position)) } }
    }

    private fun parsePositions() = input.split(",").map { position -> position.toInt() }
    private fun constantCost(distance: Int) = distance
    private fun increasedCost(distance: Int) = ((distance + 1) * distance) / 2
}