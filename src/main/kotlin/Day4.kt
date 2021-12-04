class Day4(private val input: List<String>) {

    private val sequence = input.first().split(",").map { number -> number.toInt() }

    fun part1() = solve().minByOrNull { board -> board.marked.size }!!.score()
    fun part2() = solve().maxByOrNull { board -> board.marked.size }!!.score()

    private fun solve() = sequence.fold(parseBoards()) { boards, number -> boards.map { board -> board.mark(number) } }

    private fun parseBoards() = input.drop(2).windowed(5, 6)
        .map { board -> board.map { row -> row.split(" ").mapNotNull { value -> value.toIntOrNull() } } }
        .map { values -> Board(values) }

    data class Board(val values: List<List<Int>>, val marked: List<Int> = emptyList()) {

        fun mark(number: Int) = if (isWinning()) this else Board(values, marked + number)

        fun score() = (values.flatten() - marked.toSet()).sum() * marked.last()

        private fun isWinning() = values.any { row -> marked.containsAll(row) }
                || (0..4).any { x -> (0..4).all { y -> values[y][x] in marked } }
    }
}