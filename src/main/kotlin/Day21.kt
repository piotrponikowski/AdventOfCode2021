import kotlin.math.max

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

    fun loop(): Long {
        var player1 = init1
        var player2 = init2

        var turns = 0L
        while (true) {
            player1 = stepPlayer(player1)
            turns++

            if (player1.score >= 1000) {
                return player2.score * turns * 3
            }

            player2 = stepPlayer(player2)
            turns++


            if (player2.score >= 1000) {
                return player1.score * turns * 3
            }
        }
    }

    val range = (1..3)
 
    data class State(val player1: Player,val player2: Player, val currentPlayer: Int)

    val cache = mutableMapOf<State, Pair<Long,Long>>()
    
    fun loop2(player1: Player, player2: Player, currentPlayer: Int = 1) : Pair<Long,Long> {
        val key = State(player1, player2, currentPlayer)

        if(cache.containsKey(key)) {
            return cache[key]!!
        } 
        
        var wins1 = 0L
        var wins2 = 0L
        
        for (dice1 in range) {
            for (dice2 in range) {
                for (dice3 in range) {
                    if (currentPlayer == 1) {
                        var position1 = player1.position + dice1 + dice2 + dice3
                        while (position1 > 10) position1 -= 10

                        val score1 = player1.score + position1

                        if (score1 >= 21) {
                            wins1++
                        } else {
                            val (w1, w2) = loop2(Player(position1, score1), player2, 2)
                            cache[State(Player(position1, score1), player2, 2)] = w1 to w2
                            
                            wins1 += w1
                            wins2 += w2
                        }
                    } else {
                        var position2= player2.position + dice1 + dice2 + dice3
                        while (position2 > 10) position2 -= 10

                        val score2 = player2.score + position2

                        if (score2 >= 21) {
                            wins2++
                        } else {
                            val (w1, w2) = loop2(player1, Player(position2, score2), 1)
                            cache[State(player1, Player(position2, score2), 1)] = w1 to w2
                            
                            wins1 += w1
                            wins2 += w2
                        }
                    }
                }
            }
        }
        return wins1 to wins2
    }

    private fun stepPlayer(start: Player): Player {

        var position = start.position + dice.next() + dice.next() + dice.next()
        while (position > 10) position -= 10


        val score = start.score + position
        println("start: ${start.position}, position: $position, score: $score")
        return Player(position, score)
    }

    data class Player(val position: Int, val score: Int)

    fun part2()  {
        val (r1, r2) = loop2(init1, init2, 1)
        println(max(r1, r2))
    }
}

fun main() {
    val input = readLines("day21.txt", true)
    println(Day21(2, 8).part2())
}