class Day17(val input: String) {

    val targetArea = Regex("""^target area: x=(-?\d+)..(-?\d+), y=(-?\d+)..(-?\d+)${'$'}""")
        .matchEntire(input)!!.destructured.let { (x1, x2, y1, y2) ->
            Point(x1.toInt(), y1.toInt()) to Point(
                x2.toInt(),
                y2.toInt()
            )
        }

    fun part1(): Int {
//1176
        var result = Int.MIN_VALUE
        (-200..200).forEach { x ->
            (-200..1000).forEach { y ->
                val maxY = testVelocity(Point(x, y))
                //println("$x $y $maxY")

                if (maxY > result) {
                    result = maxY
                    println(result)
                }
            }
        }

        return result
    }
    
    fun testVelocityX(initialVelocityX: Int) {
        var vx = initialVelocityX
        var px = 0
        
    }

    fun testVelocity(initialVelocity: Point): Int {
        var velocity = initialVelocity
        var position = Point(0, 0)

        var maxY = Int.MIN_VALUE
        repeat(10000) { step ->
            position += velocity
            //println("$step $position $velocity")

            if (position.y > maxY) {
                maxY = position.y
            }
            
            if(position.y < targetArea.second.y) {
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

    fun part2() = 2


    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    }
}

fun main() {
    val input = readText("day17.txt", false)
    println(Day17(input).part1())
//    println(Day17(input).isWithinTarget(Day17.Point(28, -7)))

}