class Day6(private val input: String) {

    fun part1() = solve(80).sum()
    fun part2() = solve(256).sum()

    private fun solve(days: Int) = (1..days).fold(parseState()) { state, _ -> day(state) }

    private fun parseState() = input.split(",").map { timer -> timer.toInt() }
        .let { fish -> (0..8).map { timerGroup -> fish.count { timer -> timer == timerGroup }.toLong() } }

    private fun day(state: List<Long>) = state.mapIndexed { timer, _ ->
        when (timer) {
            8 -> state[0]
            6 -> state[7] + state[0]
            else -> state[timer + 1]
        }
    }
}