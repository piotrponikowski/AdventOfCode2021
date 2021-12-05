import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day5Test : FunSpec({

    val realInput = readLines("day5.txt")
    val exampleInput = readLines("day5.txt", true)

    context("Part 1") {
        test("should solve example") {
            Day5(exampleInput).part1() shouldBe 5
        }

        test("should solve real input") {
            Day5(realInput).part1() shouldBe 7644
        }
    }

    context("Part 2") {
        test("should solve example") {
            Day5(exampleInput).part2() shouldBe 12
        }
        
        test("should solve real input") {
            Day5(realInput).part2() shouldBe 18627
        }
    }
})
