import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day4Test : FunSpec({

    val realInput = readLines("day4.txt")
    val exampleInput = readLines("day4.txt", true)

    context("Part 1") {
        test("should solve example") {
            Day4(exampleInput).part1() shouldBe 4512
        }

        test("should solve real input") {
            Day4(realInput).part1() shouldBe 60368
        }
    }

    context("Part 2") {
        test("should solve example") {
            Day4(exampleInput).part2() shouldBe 1924
        }
        
        test("should solve real input") {
            Day4(realInput).part2() shouldBe 17435
        }
    }
})
