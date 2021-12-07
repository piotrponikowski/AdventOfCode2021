import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day7Test : FunSpec({

    val realInput = readText("day7.txt")
    val exampleInput = readText("day7.txt", true)

    context("Part 1") {
        test("should solve example") {
            Day7(exampleInput).part1() shouldBe 37
        }

        test("should solve real input") {
            Day7(realInput).part1() shouldBe 344138
        }
    }

    context("Part 2") {
        test("should solve example") {
            Day7(exampleInput).part2() shouldBe 168
        }
        
        test("should solve real input") {
            Day7(realInput).part2() shouldBe 94862124
        }
    }
})
