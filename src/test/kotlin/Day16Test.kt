import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe

class Day16Test : FunSpec({

    val realInput = readText("day16.txt")

    context("Part 1") {

        test("should solve example") {
            table(
                headers("packet", "result"),
                row("8A004A801A8002F478", 16),
                row("620080001611562C8802118E34", 12),
                row("C0015000016115A2E0802F182340", 23),
                row("A0016C880162017C3686B18A3D4780", 31),
            ).forAll { input, result ->
                Day16(input).part1() shouldBe result
            }
        }

        test("should solve real input") {
            Day16(realInput).part1() shouldBe 929
        }
    }

    context("Part 2") {

        test("should solve example") {
            table(
                headers("packet", "result"),
                row("C200B40A82", 3),
                row("04005AC33890", 54),
                row("880086C3E88112", 7),
                row("CE00C43D881120", 9),
                row("D8005AC2A8F0", 1),
                row("F600BC2D8F", 0),
                row("9C005AC2F8F0", 0),
                row("9C0141080250320F1802104A08", 1),
            ).forAll { input, result ->
                Day16(input).part2() shouldBe result
            }
        }

        test("should solve real input") {
            Day16(realInput).part2() shouldBe 911945136934
        }
    }
})
