class Day21(val p1: Int, p2: Int) {
    val init1 = Player(p1, 0)
    val init2 = Player(p2, 0)


    val dice = sequence {
        var value = 1
        while (true) {
            yield(value)
            value = if (value == 100) 1 else value + 1
        }
    }.iterator()

    fun part1() = loop()
    
    fun loop() : Long {
        var player1 = init1
        var player2 = init2

        var turns = 0L
        while(true) {
            player1 = stepPlayer(player1)
            turns++
            
            if(player1.score >= 1000) {
                return player2.score * turns * 3
            }

            player2 = stepPlayer(player2)
            turns++


            if(player2.score >= 1000) {
                return player1.score * turns * 3
            }
        }
    }

    private fun stepPlayer(start: Player) :Player {
        
        var position = start.position + dice.next() + dice.next() + dice.next()
        while(position > 10) position -= 10
        
        
        val score =  start.score + position
        println("start: ${start.position}, position: $position, score: $score")
        return Player(position, score)
    }

    data class Player(val position: Int, val score: Int)

    fun part2() = 2
}

fun main() {
    val input = readLines("day21.txt", true)
    println(Day21(2, 8).part1())
}