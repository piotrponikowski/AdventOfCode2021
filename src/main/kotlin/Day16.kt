class Day16(val input: String) {

    val sequence = input
        .map { it.toString().toInt(16).toString(2).padStart(4, '0') }
        .joinToString("")

    fun part1():Int {
        val (packets, _) = parse(sequence)
        val versionSum = scorePackets(packets)
        return versionSum
    }

    fun part2():Int {
        val (packets, _) = parse(sequence)
        val versionSum = scorePackets(packets)
        return versionSum
    }

    fun calcPackets(packets: List<Packet>, sum: Int = 0) : Int {
        var result = sum
        for(packet in packets) {
            result += packet.version
            result += scorePackets(packet.packets)
        }
        return result
    }


    fun scorePackets(packets: List<Packet>, sum: Int = 0) : Int {
        var result = sum
        for(packet in packets) {
            result += packet.version
            result += scorePackets(packet.packets)
        }
        return result
    }
    

    fun parse(input: String, expectedPackets: Int = 1, paddingNeeded: Boolean = true): Pair<List<Packet>, String> {
        var data = input
        val packets = mutableListOf<Packet>()

        while (data.isNotEmpty()) {

            val version = data.take(3).toInt(2)
            data = data.drop(3)

            val type = data.take(3).toInt(2)
            data = data.drop(3)

            if (type == 4) {
                var literalData = ""
                var more = true
                while (more) {
                    val next = data.take(5)
                    data = data.drop(5)

                    more = next[0] == '1'
                    literalData += next.drop(1)
                }
                packets.add(Packet(version, type, literalData.toLong(2)))
                
                if(paddingNeeded) {
                    val padding = data.length % 4
                    data = data.drop(padding)
                }
            } else {
                val lengthType = data.take(1)
                data = data.drop(1)

                if (lengthType == "0") {
                    val totalLengthInBits = data.take(15).toInt(2)
                    data = data.drop(15)

                    val (subs, data2) = parse(data.take(totalLengthInBits), 99, false)
                    data = data.drop(totalLengthInBits)
//                    data = data2
                    
                    packets.add(Packet(version, type, 0, subs))
                    

                } else {
                    val numberOfSubPackets = data.take(11).toInt(2)
                    data = data.drop(11)

                    val (subs, data2)  = parse(data, numberOfSubPackets, false)
                    packets.add(Packet(version, type, 0, subs))
                    data = data2
                }
            }
            


            println(packets.last())
            if(packets.size == expectedPackets || data.isEmpty()) {
                break
            }
        }
        return packets to data
    }

    data class Packet(val version: Int, val type: Int, val literal: Long = 0, val packets: List<Packet> = emptyList())

}

fun main() {
    val input = readText("day16.txt", true)
    println(Day16(input).part1())
}