import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe

class Day18Test : FunSpec({

    val realInput = readLines("day18.txt")
    val exampleInput = readLines("day18.txt", true)

    context("Part 1") {

        test("should reduce number") {
            table(
                headers("inputNumber", "reducedNumber"),
                row("[[[[[9,8],1],2],3],4]", "[[[[0,9],2],3],4]"),
                row("[7,[6,[5,[4,[3,2]]]]]", "[7,[6,[5,[7,0]]]]"),
                row("[[6,[5,[4,[3,2]]]],1]", "[[6,[5,[7,0]]],3]"),
                row("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]", "[[3,[2,[8,0]]],[9,[5,[7,0]]]]"),
                row("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]", "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]")
            ).forAll { inputNumber, reducedNumber ->
                Day18.Number.parse(inputNumber).reduce() shouldBe Day18.Number.parse(reducedNumber)
            }
        }

        test("should add numbers") {
            table(
                headers("number1", "number2", "sum"),
                row(
                    "[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]",
                    "[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]",
                    "[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]"
                ),
                row(
                    "[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]",
                    "[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]",
                    "[[[[6,7],[6,7]],[[7,7],[0,7]]],[[[8,7],[7,7]],[[8,8],[8,0]]]]"
                ),
                row(
                    "[[[[6,7],[6,7]],[[7,7],[0,7]]],[[[8,7],[7,7]],[[8,8],[8,0]]]]",
                    "[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]",
                    "[[[[7,0],[7,7]],[[7,7],[7,8]]],[[[7,7],[8,8]],[[7,7],[8,7]]]]"
                ),
                row(
                    "[[[[7,0],[7,7]],[[7,7],[7,8]]],[[[7,7],[8,8]],[[7,7],[8,7]]]]",
                    "[7,[5,[[3,8],[1,4]]]]",
                    "[[[[7,7],[7,8]],[[9,5],[8,7]]],[[[6,8],[0,8]],[[9,9],[9,0]]]]"
                ),
                row(
                    "[[[[7,7],[7,8]],[[9,5],[8,7]]],[[[6,8],[0,8]],[[9,9],[9,0]]]]",
                    "[[2,[2,2]],[8,[8,1]]]",
                    "[[[[6,6],[6,6]],[[6,0],[6,7]]],[[[7,7],[8,9]],[8,[8,1]]]]"
                ),
                row(
                    "[[[[6,6],[6,6]],[[6,0],[6,7]]],[[[7,7],[8,9]],[8,[8,1]]]]",
                    "[2,9]",
                    "[[[[6,6],[7,7]],[[0,7],[7,7]]],[[[5,5],[5,6]],9]]"
                ),
                row(
                    "[[[[6,6],[7,7]],[[0,7],[7,7]]],[[[5,5],[5,6]],9]]",
                    "[1,[[[9,3],9],[[9,0],[0,7]]]]",
                    "[[[[7,8],[6,7]],[[6,8],[0,8]]],[[[7,7],[5,0]],[[5,5],[5,6]]]]"
                ),
                row(
                    "[[[[7,8],[6,7]],[[6,8],[0,8]]],[[[7,7],[5,0]],[[5,5],[5,6]]]]",
                    "[[[5,[7,4]],7],1]",
                    "[[[[7,7],[7,7]],[[8,7],[8,7]]],[[[7,0],[7,7]],9]]"
                ),
                row(
                    "[[[[7,7],[7,7]],[[8,7],[8,7]]],[[[7,0],[7,7]],9]]",
                    "[[[[4,2],2],6],[8,7]]",
                    "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]"
                ),
            ).forAll { input1, input2, result ->
                Day18.Number.parse(input1).add(Day18.Number.parse(input2)) shouldBe Day18.Number.parse(result)
            }
        }

        test("should calculate magnitude") {
            table(
                headers("number", "result"),
                row("[[1,2],[[3,4],5]]", 143),
                row("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]", 1384),
                row("[[[[1,1],[2,2]],[3,3]],[4,4]]", 445),
                row("[[[[3,0],[5,3]],[4,4]],[5,5]]", 791),
                row("[[[[5,0],[7,4]],[5,5]],[6,6]]", 1137),
                row("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]", 3488),
            ).forAll { number, result ->
                Day18.Number.parse(number).magnitude() shouldBe result
            }
        }

        test("should solve example") {
            Day18(exampleInput).part1() shouldBe 4140
        }

        test("should solve real input") {
            Day18(realInput).part1() shouldBe 4391
        }
    }

    context("Part 2") {
        test("should solve example") {
            Day18(exampleInput).part2() shouldBe 3993
        }

        test("should solve real input") {
            Day18(realInput).part2() shouldBe 4626
        }
    }
})
