class Day14(val input: List<String>) {

    private val template = input.first()

    private val insertions = input.drop(2).map { it.split(" -> ") }
        .associate { (pair, insertion) -> pair to insertion }

    private val initialState = template.windowed(2)
        .groupingBy { pair -> pair }.eachCount()
        .mapValues { (_, frequency) -> frequency.toLong() }

    private val emptyState = insertions.keys.associateWith { 0L }

    fun part1() = solve(10)

    fun part2() = solve(40)

    private fun solve(steps: Int) = (0 until steps).fold(initialState) { state, _ -> step(state) }
        .let { state ->
            val frequencies = (state.map { (pair, frequency) -> pair.first() to frequency } + (template.last() to 1L))
            val scores = frequencies.groupBy { (pair, _) -> pair }.mapValues { (_, entries) -> entries.sumOf { it.second } }
            scores.maxOf { it.value } - scores.minOf { it.value }
        }

    private fun step(state: Map<String, Long>) = state.keys.fold(emptyState) { newState, pair ->
        newState.mapValues { (key, value) ->
            when (key) {
                pair.first() + insertions[pair]!! -> value + state[pair]!!
                insertions[pair]!! + pair.last() -> value + state[pair]!!
                else -> value
            }
        }
    }

}
