class Day8(private val input: List<String>) {
    
    fun part1() = parseEntries().sumOf { (_, output) -> output.count { segment -> segment.size in listOf(2, 3, 4, 7) } }

    fun part2() = parseEntries().sumOf { (patternGroup, outputGroup) ->
        detectSegments(patternGroup).let { detectedSegments ->
            outputGroup.joinToString("") { output -> detectedSegments[output].toString() }.toInt()
        }
    }

    private fun parseEntries() = input.map { line -> line.split(" | ").map { group -> group.split(" ") } }
        .map { groups -> groups.map { group -> group.map { segment -> segment.toCharArray().sorted() } } }

    private fun detectSegments(segments: List<List<Char>>) = patterns
        .fold(emptyMap<Int, List<Char>>()) { detected, pattern ->
            val nextDetected = segments.first { segment -> pattern.condition(segment, detected) }
            detected + (pattern.number to nextDetected)
        }.entries.associate { (key, value) -> value to key }

    private val patterns = listOf(
        Pattern(number = 1) { segment, _ -> segment.size == 2 },
        Pattern(number = 4) { segment, _ -> segment.size == 4 },
        Pattern(number = 7) { segment, _ -> segment.size == 3 },
        Pattern(number = 8) { segment, _ -> segment.size == 7 },
        Pattern(number = 9) { segment, detected -> segment.size == 6 && segment.containsAll(detected[4]!!) },
        Pattern(number = 6) { segment, detected -> segment.size == 6 && !segment.containsAll(detected[7]!!) },
        Pattern(number = 3) { segment, detected -> segment.size == 5 && segment.containsAll(detected[1]!!) },
        Pattern(number = 5) { segment, detected -> segment.size == 5 && detected[6]!!.containsAll(segment) },
        Pattern(number = 2) { segment, detected -> segment.size == 5 && !detected[9]!!.containsAll(segment) },
        Pattern(number = 0) { segment, detected -> segment.size == 6 && !segment.containsAll(detected[5]!!) }
    )

    data class Pattern(val number: Int, val condition: (List<Char>, Map<Int, List<Char>>) -> Boolean)
}