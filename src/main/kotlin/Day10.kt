class Day10(input: List<String>) {
    private val data = input.map { line -> line.toCharArray().toList() }

    fun part1() = data.mapNotNull { brackets -> findCorrupted(brackets) }
        .sumOf { corrupted -> getBracket(corrupted).corruptedScore }

    fun part2() = data.filter { brackets -> !isCorrupted(brackets) }
        .map { brackets -> findIncomplete(brackets).reversed() }
        .map { incomplete -> incomplete.fold(0L) { score, bracket -> score * 5 + getBracket(bracket).incompleteScore } }
        .sorted().let { scores -> scores[scores.size / 2] }

    private fun findIncomplete(chunk: List<Char>, openBrackets: List<Char> = emptyList()): List<Char> {
        return when {
            chunk.isEmpty() -> openBrackets
            isOpeningBracket(chunk.first()) -> findIncomplete(chunk.drop(1), openBrackets + chunk.take(1))
            else -> findIncomplete(chunk.drop(1), openBrackets.dropLast(1))
        }
    }

    private fun findCorrupted(chunk: List<Char>, stack: List<Char> = emptyList()): Char? {
        return when {
            chunk.isEmpty() -> null
            isOpeningBracket(chunk.first()) -> findCorrupted(chunk.drop(1), stack + chunk.take(1))
            getBracket(stack.last()).closing == chunk.first() -> findCorrupted(chunk.drop(1), stack.dropLast(1))
            else -> chunk.first()
        }
    }

    private fun isCorrupted(chunk: List<Char>) = findCorrupted(chunk) != null

    private fun isOpeningBracket(symbol: Char) = brackets.any { bracket -> symbol == bracket.opening }

    private fun getBracket(symbol: Char) =
        brackets.first { bracket -> symbol in listOf(bracket.opening, bracket.closing) }

    private val brackets = listOf(
        Bracket('(', ')', 3, 1),
        Bracket('[', ']', 57, 2),
        Bracket('{', '}', 1197, 3),
        Bracket('<', '>', 25137, 4)
    )

    data class Bracket(val opening: Char, val closing: Char, val corruptedScore: Int, val incompleteScore: Int)
}