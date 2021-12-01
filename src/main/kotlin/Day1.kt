class Day1(input: List<String>) {

    private val measurements = input.map { measurement -> measurement.toInt() }

    fun part1() = solve(1)

    fun part2() = solve(3)

    private fun solve(windowSize: Int) = measurements
        .windowed(windowSize).map { group -> group.sum() }
        .zipWithNext().count { (current, next) -> next > current }
}
