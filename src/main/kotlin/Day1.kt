class Day1(input: List<String>) {

    private val measurements = input.map { digit -> digit.toInt() }

    fun part1() = measurements
        .zipWithNext()
        .count { (current, next) -> next > current }

    fun part2() = measurements.windowed(3)
        .map { group -> group.sum() }
        .zipWithNext()
        .count { (current, next) -> next > current }

}
