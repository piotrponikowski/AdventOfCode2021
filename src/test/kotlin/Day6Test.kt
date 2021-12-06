import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day6Test : FunSpec({

    val realInput = readText("day6.txt")
    val exampleInput = readText("day6.txt", true)

    context("Part 1") {
        test("should solve example") {
            Day6(exampleInput).part1() shouldBe 5934
        }

        test("should solve real input") {
            Day6(realInput).part1() shouldBe 390011
        }
    }

    context("Part 2") {
        test("should solve example") {
            Day6(exampleInput).part2() shouldBe 26984457539
        }
        
        test("should solve real input") {
            Day6(realInput).part2() shouldBe 1746710169834
        }
    }
})
