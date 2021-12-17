class Day17(val input: String) {

    val targetArea = Regex("""^target area: x=(-?\d+)..(-?\d+), y=(-?\d+)..(-?\d+)${'$'}""")
        .matchEntire(input)!!.destructured
        .let { (x1, x2, y1, y2) -> Point(x1.toInt(), y1.toInt()) to Point(x2.toInt(), y2.toInt()) }

    fun part1() = 1
    fun part2(): Int {
        val possibleX = (-1000..1000).filter { vx -> testVelocityX(vx) }

        println(possibleX)

        var result = 0
        possibleX.forEach { vx ->
            println("Find for vx = $vx")

            val possibleY = (-1000..1000).filter { vy -> testVelocityXY(vx, vy) }
            println("found = $possibleY")
            result += possibleY.size
        }

        return result
    }

    fun testVelocityXY(initialVelocityX: Int, initialVelocityY: Int): Boolean {


        val (a1, a2) = targetArea

        var vx = initialVelocityX
        var vy = initialVelocityY

        var py = 0
        var px = 0

        var step = 0
        while (true) {

            if (step > 1000) {
                return false
            }


            px += vx
            py += vy


            vx = when {
                vx < 0 -> vx + 1
                vx > 0 -> vx - 1
                else -> vx
            }
            vy -= 1

            if (isWithinTarget(Point(px, py))) {
                return true
            }

            step++
        }
    }

    fun testVelocityX(initialVelocityX: Int): Boolean {
        val (a1, a2) = targetArea

        var vx = initialVelocityX
        var px = 0
        val result = mutableListOf<Int>()

        var step = 0
        while (true) {
            if ((vx == 0 && (px < a1.x || px > a2.x))) {
                return false
            }

            px += vx
            vx = when {
                vx < 0 -> vx + 1
                vx > 0 -> vx - 1
                else -> vx
            }

            if (px >= a1.x && px <= a2.x) {
                return true
            }
        }
    }

    fun testVelocity(initialVelocity: Point): Int {
        var velocity = initialVelocity
        var position = Point(0, 0)

        var maxY = Int.MIN_VALUE
        repeat(10000) { step ->
            position += velocity
            println("$step $position $velocity")

            if (position.y > maxY) {
                maxY = position.y
            }

            if (position.y < targetArea.first.y) {
                return Int.MIN_VALUE
            }


            val newVelocityX = when {
                velocity.x < 0 -> velocity.x + 1
                velocity.x > 0 -> velocity.x - 1
                else -> velocity.x
            }

            val newVelocityY = velocity.y - 1

            velocity = Point(newVelocityX, newVelocityY)

            if (isWithinTarget(position)) {
                return maxY
            }
        }

        return Int.MIN_VALUE
    }

    fun isWithinTarget(position: Point): Boolean {
        val (a1, a2) = targetArea
        return position.x >= a1.x && position.x <= a2.x
                && position.y >= a1.y && position.y <= a2.y
    }


    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    }
}

fun main() {
    //2364
    //4716
    val input = readText("day17.txt", false)
//    println(Day17(input).testVelocityY(0, listOf(0, 1, 2, 3, 4, 5, 6, 7)))
    println(Day17(input).part2())
//    println(Day17(input).testVelocityXY(30, -10))

}