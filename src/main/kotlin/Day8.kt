class Day8(private val input: List<String>) {

    val digits = input.map { line ->
        line.split(" | ").map { it.split(" ").map { it.toCharArray().sorted().joinToString("") } }
    }

    fun part1() = digits.sumOf { (a, b) -> b.count { word -> word.length in listOf(2, 3, 4, 7) } }
    fun part2(): Int {
        var sum = 0
        digits.forEach { (a, b) ->
            val dict = mutableMapOf<String, Int>()

            val d1 = a.find { word -> word.length == 2 }!!
            dict[d1] = 1

            val d4 = a.find { word -> word.length == 4 }!!
            dict[d4] = 4

            val d7 = a.find { word -> word.length == 3 }!!
            dict[d7] = 7

            val d8 = a.find { word -> word.length == 7 }!!
            dict[d8] = 8

            val d9 = a.find { word -> word.length == 6 && d4.toCharArray().all { d -> word.contains(d) } }!!
            dict[d9] = 9

            var remaining = a.filter { word -> !dict.contains(word) }
            val d0 = remaining.find { word -> word.length == 6 && d1.toCharArray().all { d -> word.contains(d) } }!!
            dict[d0] = 0

            remaining = a.filter { word -> !dict.contains(word) }
            val d6 = remaining.find { word -> word.length == 6 }!!
            dict[d6] = 6

            remaining = a.filter { word -> !dict.contains(word) }
            val d3 = remaining.find { word -> word.length == 5 && d1.toCharArray().all { d -> word.contains(d) } }!!
            dict[d3] = 3

            remaining = a.filter { word -> !dict.contains(word) }
            val d5 =
                remaining.find { word -> word.length == 5 && d4.toCharArray().count { d -> word.contains(d) } == 3 }!!
            dict[d5] = 5

            remaining = a.filter { word -> !dict.contains(word) }
            val d2 = remaining.find { word -> word.length == 5 }!!
            dict[d2] = 2

            val test = b.map { w -> dict[w]!!.toString() }.joinToString("").toInt()
            sum += test


            println("$a, $dict, $test")

        }
        return sum
    }
}

fun main() {
    val input = readLines("day8.txt", false)
    println(Day8(input).part2())
}