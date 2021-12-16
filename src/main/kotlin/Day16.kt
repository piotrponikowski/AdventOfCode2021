class Day16(val input: String) {

    fun part1() = parseInput().parsePacket().sumVersions()
    fun part2() = parseInput().parsePacket().computeValue()

    private fun parseInput() = input
        .map { message -> message.toString().toInt(16).toString(2) }
        .flatMap { part -> part.padStart(4, '0').toCharArray().toList() }
        .let { sequence -> DataStream(sequence) }

    class DataStream(val data: List<Char>, private var index: Int = 0) {

        fun parsePacket(): Packet {
            val version = takeInt(3)
            val type = takeInt(3)

            return if (type == 4) {
                Packet(version, type, value = extractLiteral())
            } else {
                val lengthType = takeInt(1)
                if (lengthType == 0) {
                    val subPacketsLength = takeInt(15)
                    Packet(version, type, packets = extractPacketsByLength(index + subPacketsLength))
                } else {
                    val subPacketsCount = takeInt(11)
                    Packet(version, type, packets = extractPacketsByCount(subPacketsCount))
                }
            }
        }

        private fun extractLiteral(buffer: List<Char> = emptyList()): Long {
            val hasNext = takeInt(1) == 1
            val newBuffer = buffer + take(4)

            return if (hasNext) extractLiteral(newBuffer)
            else newBuffer.joinToString("").toLong(2)
        }

        private fun extractPacketsByLength(maxIndex: Int, packets: List<Packet> = emptyList()): List<Packet> {
            return if (index == maxIndex) packets
            else extractPacketsByLength(maxIndex, packets + parsePacket())
        }

        private fun extractPacketsByCount(count: Int) = (0 until count).map { parsePacket() }

        private fun take(count: Int) = data.subList(index, index + count).also { index += count }

        private fun takeInt(count: Int) = take(count).joinToString("").toInt(2)
    }

    data class Packet(val version: Int, val type: Int, val value: Long = 0, val packets: List<Packet> = emptyList()) {
        
        fun sumVersions(): Int = version + packets.sumOf { subPacket -> subPacket.sumVersions() }
        
        fun computeValue(): Long {
            val values = packets.map { subPacket -> subPacket.computeValue() }
            return when (type) {
                0 -> values.reduce { a, b -> a + b }
                1 -> values.reduce { a, b -> a * b }
                2 -> values.minOf { subPacket -> subPacket }
                3 -> values.maxOf { subPacket -> subPacket }
                4 -> value
                5 -> values.let { (a, b) -> if (a > b) 1 else 0 }
                6 -> values.let { (a, b) -> if (a < b) 1 else 0 }
                7 -> values.let { (a, b) -> if (a == b) 1 else 0 }
                else -> error("Unknown packet type.")
            }
        }
    }
}