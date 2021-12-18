class Day18(val input: List<String>) {

    private val numbers = input.map { line -> Number.parse(line) }

    fun part1() = numbers.drop(1).fold(numbers.first()) { sum, number -> sum.add(number) }.magnitude()

    fun part2() = numbers.flatMap { number1 -> numbers.map { number2 -> number1 to number2 } }
        .filter { (number1, number2) -> number1 != number2 }
        .map { (number1, number2) -> number1.add(number2).magnitude() }
        .maxOf { magnitude -> magnitude }

    data class Part(val value: Int, val level: Int)

    data class Number(val parts: List<Part>) {

        fun reduce() = Number(reduce(parts))
        fun magnitude() = magnitude(parts)
        fun add(other: Number) = Number((parts + other.parts).map { part -> Part(part.value, part.level + 1) }).reduce()

        private fun reduce(parts: List<Part>): List<Part> {
            parts.indices.find { index -> parts[index].level > 4 }?.let { indexToExplode ->
                return reduce(parts.mapIndexedNotNull { index, part ->
                    when (index) {
                        indexToExplode - 1 -> {
                            val part1 = parts[indexToExplode - 1]
                            val part2 = parts[indexToExplode]
                            Part(part1.value + part2.value, part1.level)
                        }
                        indexToExplode -> Part(0, 4)
                        indexToExplode + 1 -> null
                        indexToExplode + 2 -> {
                            val part1 = parts[indexToExplode + 1]
                            val part2 = parts[indexToExplode + 2]
                            Part(part1.value + part2.value, part2.level)
                        }
                        else -> part
                    }
                })
            }

            parts.indices.find { index -> parts[index].value > 9 }?.let { indexToSplit ->
                return reduce(parts.flatMapIndexed { index, part ->
                    when (index) {
                        indexToSplit -> listOf(
                            Part(part.value / 2, part.level + 1),
                            Part(part.value / 2 + part.value % 2, part.level + 1)
                        )
                        else -> listOf(part)
                    }
                })
            }

            return parts
        }

        private fun magnitude(parts: List<Part>): Int {
            if (parts.size == 1) return parts.first().value

            val indexToMerge = parts.indices.toList().dropLast(1)
                .first { index -> parts[index].level == parts[index + 1].level }

            return magnitude(parts.mapIndexedNotNull { index, part ->
                when (index) {
                    indexToMerge -> {
                        val part1 = parts[indexToMerge]
                        val part2 = parts[indexToMerge + 1]
                        Part(part1.value * 3 + part2.value * 2, part1.level - 1)
                    }
                    indexToMerge + 1 -> null
                    else -> part
                }
            })
        }

        companion object {
            fun parse(input: String) = Number(parseParts(input))

            private fun parseParts(input: String, level: Int = 0, parts: List<Part> = emptyList()): List<Part> {
                return if (input.isEmpty()) parts
                else when (val current = input.first()) {
                    '[' -> parseParts(input.drop(1), level + 1, parts)
                    ']' -> parseParts(input.drop(1), level - 1, parts)
                    in '0'..'9' -> parseParts(input.drop(1), level, parts + Part(current.toString().toInt(), level))
                    else -> parseParts(input.drop(1), level, parts)
                }
            }
        }
    }
}
