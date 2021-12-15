import kotlin.math.min

class Day15(val input: List<String>) {
    val points =
        input.flatMapIndexed { y, line -> line.mapIndexed { x, value -> Point(x, y) to value.toString().toInt() } }
            .toMap()

    val width = input.first().length
    val height = input.size

    val points2 = (0 until 5).flatMap { ty ->
        (0 until 5).flatMap { tx ->
            val delta = ty + tx

            points.map { (point, value) ->
                val newPoint = Point(point.x + (tx * width), point.y + (ty * height))
                var newRisk = (value + delta)
                while (newRisk > 9) {
                    newRisk -= 9
                }


                newPoint to newRisk
            }
        }
    }.toMap()

    fun part1() = solve(points)
    fun part2() = solve(points2)

    fun solve(tiles: Map<Point, Int>) {
        val start = Point(0, 0)
        val visited = mutableSetOf<Point>()
        val pointsForCheck = mutableListOf(start)
        val totalDistance = mutableMapOf(start to tiles[start]!!)
//        val queue = PriorityQueue<Point>()  {t1, t2 -> t1.count - t2.count}


        while (pointsForCheck.isNotEmpty()) {
            val currentPoint = pointsForCheck.removeFirst()
//            val currentPoint = pointsForCheck.minByOrNull { p -> totalDistance[p]!! }!!
//            pointsForCheck.remove(currentPoint)
//            println(pointsForCheck.size)

//            if(currentPoint !in visited) {
            val currentDistance = totalDistance[currentPoint]!!

            val neighbours = directions.map { direction -> currentPoint + direction }
                .filter { neighbour -> neighbour in tiles }
//                    .filter { neighbour -> neighbour !in visited }


            neighbours.forEach { neighbour ->
                val nextDistance = currentDistance + tiles[neighbour]!!
                val nextTotalDistance = totalDistance[neighbour] ?: Int.MAX_VALUE
                totalDistance[neighbour] = min(nextDistance, nextTotalDistance)

                if (nextDistance < nextTotalDistance) {
                    pointsForCheck += neighbour
                }
            }

              
        }

        val xMax = totalDistance.keys.maxOf { point -> point.x }
        val yMax = totalDistance.keys.maxOf { point -> point.y }

//        println(printPoints(totalDistance))
        println(totalDistance[Point(xMax, yMax)]!! - 1)
    }

    private fun printPoints(state: Map<Point, Int>): String {
        val xMax = state.keys.maxOf { point -> point.x }
        val xMin = state.keys.minOf { point -> point.x }
        val yMax = state.keys.maxOf { point -> point.y }
        val yMin = state.keys.minOf { point -> point.y }

        return (yMin..yMax).joinToString("\n") { y ->
            (xMin..xMax).joinToString("") { x ->
                state[Point(x, y)].toString() + "\t"
            }
        }
    }

    private val directions = listOf(Point(-1, 0), Point(1, 0), Point(0, -1), Point(0, 1))

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    }
}

fun main() {
    // 2851
    // 2844
    val input = readLines("day15.txt", false)
    println(Day15(input).part1())
    println(Day15(input).part2())

}
