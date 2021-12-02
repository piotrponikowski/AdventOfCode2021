class Day2(input: List<String>) {

    private val instructions = input.map { line -> line.split(" ") }
        .map { (command, value) -> Instruction(Command.valueOf(command.uppercase()), value.toInt()) }

    fun part1() = instructions
        .fold(Position(0, 0)) { (x, y), (command, value) ->
            when (command) {
                Command.FORWARD -> Position(x + value, y)
                Command.DOWN -> Position(x, y + value)
                Command.UP -> Position(x, y - value)
            }
        }.let { (x, y) -> x * y }

    fun part2() = instructions
        .fold(Position(0, 0, 0)) { (x, y, aim), (command, value) ->
            when (command) {
                Command.FORWARD -> Position(x + value, y + value * aim, aim)
                Command.DOWN -> Position(x, y, aim + value)
                Command.UP -> Position(x, y, aim - value)
            }
        }.let { (x, y) -> x * y }

    enum class Command { FORWARD, DOWN, UP }
    data class Instruction(val command: Command, val value: Int)
    data class Position(val x: Int, val y: Int, val aim: Int = 0)
}
