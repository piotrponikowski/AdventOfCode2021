class Day6(private val input: String) {


    fun part1() = solve(80).values.sum()

    fun part2() = solve(256).values.sum()

    private fun solve(days: Int) = (1..days).fold(parseState()) { state, _ -> day(state) }

    private fun parseState() = input.split(",").map { it.toInt() }
        .groupingBy { it }.eachCount().mapValues { (_, b) -> b.toLong() }
    
    private fun day(state: Map<Int, Long>): Map<Int, Long> {
        val nextState = state.mapKeys { (key) -> key - 1 }.toMutableMap()
        nextState[6] = (nextState[6] ?: 0) + (nextState[-1] ?: 0)
        nextState[8] = (nextState[-1] ?: 0)
        nextState.remove(-1)
        return nextState
    }
}