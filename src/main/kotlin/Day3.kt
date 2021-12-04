class Day3(private val input: List<String>) {

    private val reportLength = input.maxOf { report -> report.length }

    fun part1() = gamma * epsilon
    fun part2() = scrubber * generator

    private val gamma = groupBits(::mostCommon)
    private val epsilon = groupBits(::leastCommon)
    private val generator = filterBits(::mostCommon)
    private val scrubber = filterBits(::leastCommon)

    private fun mostCommon(list: List<Char>) = list.count { digit -> digit == '1' }
        .let { count -> if (count * 2 >= list.size) '1' else '0' }

    private fun leastCommon(list: List<Char>) = list.count { digit -> digit == '1' }
        .let { count -> if (count * 2 >= list.size) '0' else '1' }

    private fun groupBits(selectionMethod: (List<Char>) -> Char) = (0 until reportLength)
        .map { index -> selectionMethod(input.map { report -> report[index] }) }
        .joinToString("").toInt(2)

    private fun filterBits(selectionMethod: (List<Char>) -> Char) = (0 until reportLength)
        .fold(input) { reports, index ->
            val filterDigit = selectionMethod(reports.map { report -> report[index] })
            reports.filter { report -> report[index] == filterDigit || reports.size == 1 }
        }
        .joinToString("").toInt(2)
}


