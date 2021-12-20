class Day14(val input: List<String>) {

    private val template = input.first()

    private val insertions = input.drop(2).map { line -> line.split(" -> ") }
        .associate { (pair, insertion) -> pair to insertion }

    private val initialState = template.windowed(2)
        .groupingBy { pair -> pair }.eachCount()
        .mapValues { (_, count) -> count.toLong() }

    private val emptyState = insertions.keys.associateWith { 0L }

    fun part1() = solve(10)

    fun part2() = solve(40)

    private fun solve(steps: Int) = (0 until steps)
        .fold(initialState) { state, _ -> step(state) }
        .let { state ->
            val scores = (state.map { (pair, count) -> pair.first() to count } + (template.last() to 1L))
                .groupBy { (type, _) -> type }.values
                .map { partialCount -> partialCount.sumOf { (_, count) -> count } }

            val maxCount = scores.maxOf { count -> count }
            val minCount = scores.minOf { count -> count }
            maxCount - minCount
        }

    private fun step(state: Map<String, Long>) = state.keys.fold(emptyState) { newState, pair ->
        val insertion = insertions[pair]!!
        val frequency = state[pair]!!

        val pair1 = pair.first() + insertion
        val pair2 = insertion + pair.last()

        newState.mapValues { (key, value) ->
            when (key) {
                pair1 -> value + frequency
                pair2 -> value + frequency
                else -> value
            }
        }
    }
}
