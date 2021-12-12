class Day12(input: List<String>) {
    private val connections = input.map { line -> line.split("-") }
        .flatMap { (caveFrom, caveTo) -> listOf(caveFrom to caveTo, caveTo to caveFrom) }
        .groupBy({ (caveFrom) -> caveFrom }, { (_, caveTo) -> caveTo })

    fun part1() = search(0).size
    fun part2() = search(1).size

    private fun search(maxRevisits: Int): List<List<String>> {
        val finished = mutableListOf<List<String>>()
        val notFinished = mutableListOf(listOf("start"))

        while (notFinished.isNotEmpty()) {
            val currentPath = notFinished.removeFirst()
            val nextCaves = connections[currentPath.last()]!!
            val smallRevisited = countSmallRevisited(currentPath)

            for (nextCave in nextCaves) {
                val alreadyVisited = currentPath.contains(nextCave)

                when {
                    nextCave == "start" -> continue
                    nextCave == "end" -> finished += currentPath + nextCave
                    isBig(nextCave) -> notFinished += currentPath + nextCave
                    isSmall(nextCave) && (!alreadyVisited || smallRevisited < maxRevisits) -> notFinished += currentPath + nextCave
                }
            }
        }

        return finished
    }

    private fun isSmall(cave: String) = cave.all { letter -> letter.isLowerCase() }
    private fun isBig(cave: String) = cave.all { letter -> letter.isUpperCase() }

    private fun countSmallRevisited(path: List<String>) = path
        .filter { cave -> isSmall(cave) }.groupingBy { cave -> cave }.eachCount()
        .filterValues { count -> count > 1 }.size
}