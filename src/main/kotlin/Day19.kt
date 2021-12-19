import kotlin.math.abs

class Day19(val input: List<String>) {

    private fun parse(): List<Scanner> {
        val scannerRegex = Regex("""--- scanner (\d+) ---""")
        val beaconRegex = Regex("""(-?\d+),(-?\d+),(-?\d+)""")
        var beacons = mutableListOf<Point>()
        val scanners = mutableListOf<Scanner>()

        var beaconIndex = 0
        for (line in input) {
            if (scannerRegex.matches(line)) {
                beaconIndex = scannerRegex.matchEntire(line)!!.destructured.let { (index) -> index.toInt() }
                beacons = mutableListOf()
                scanners.add(Scanner(beaconIndex, beacons))

            } else if (beaconRegex.matches(line)) {
                beacons += beaconRegex.matchEntire(line)!!.destructured
                    .let { (x, y, z) -> Point(x.toInt(), y.toInt(), z.toInt()) }
            }
        }

        return scanners
    }

    fun part1() = solve().keys.flatMap { it.beacons }.distinct().count()

    fun part2() = solve().values.let { scanners ->
        scanners.flatMap { s1 ->
            scanners.map { s2 ->
                abs(s1.x - s2.x) + abs(s1.y - s2.y) + abs(s1.z - s2.z)
            }
        }
    }.maxOf { it }

    fun solve(): Map<Scanner, Point> {
        val scanners = parse().toMutableList()

        val solvedIndexes = mutableSetOf(scanners[0].index)
        val solvedScanners = mutableMapOf(scanners[0] to Point(0, 0, 0))

        while (solvedScanners.size < scanners.size) {
            val scannersForSearch = scanners.filter { scanner -> scanner.index !in solvedIndexes }

            val newSolved = mutableMapOf<Scanner, Point>()
            for (scanner in scannersForSearch) {
                for (solvedScanner in solvedScanners.keys) {
                    matchScanner(scanner, solvedScanner)?.let { (matchedScanner, delta) ->
                        newSolved[Scanner(scanner.index, matchedScanner.beacons.map { it - delta })] = delta
                        solvedIndexes += scanner.index
                    }
                }
            }

            println(solvedScanners.size)
            solvedScanners += newSolved
        }

        return solvedScanners
    }

    private fun matchScanner(scanner: Scanner, solvedScanner: Scanner): Pair<Scanner, Point>? {
        for (direction in directions) {
            for (rotation in rotations) {
                val adjustedBeacons = scanner.beacons.map { beacon -> direction(beacon) * rotation }
                val delta = matchBeacons(adjustedBeacons, solvedScanner.beacons)

                if (delta != null) {
                    return Scanner(scanner.index, adjustedBeacons) to delta
                }
            }
        }

        return null
    }

    private fun matchBeacons(beacons: List<Point>, solvedBeacons: List<Point>): Point? {
        for (beacon in beacons) {
            for (referenceBeacon in solvedBeacons) {
                val delta = beacon - referenceBeacon
                val count = beacons.map { it - delta }.count { it in solvedBeacons }
                if (count >= 12) {
                    return delta
                }
            }
        }
        return null
    }

    private val rotations = listOf(-1, 1)
        .let { rotation -> rotation.flatMap { x -> rotation.flatMap { y -> rotation.map { z -> Point(x, y, z) } } } }

    private val directions = listOf<(Point) -> Point>(
        { point -> Point(point.x, point.y, point.z) },
        { point -> Point(point.x, point.z, point.y) },
        { point -> Point(point.y, point.x, point.z) },
        { point -> Point(point.y, point.z, point.x) },
        { point -> Point(point.z, point.x, point.y) },
        { point -> Point(point.z, point.y, point.x) },
    )

    data class Scanner(val index: Int, val beacons: List<Point>)

    data class Point(val x: Int, val y: Int, val z: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y, z + other.z)
        operator fun minus(other: Point) = Point(x - other.x, y - other.y, z - other.z)
        operator fun times(other: Point) = Point(x * other.x, y * other.y, z * other.z)
    }
}

fun main() {
    val input = readLines("day19.txt", false)
    println(Day19(input).part2())

}