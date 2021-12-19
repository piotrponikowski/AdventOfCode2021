import kotlin.math.abs

class Day19(val input: String) {

    fun part1() = solve().flatMap { scanner -> scanner.beacons }.distinct().count()

    fun part2() = solve().map { scanner -> scanner.position }
        .let { position -> position.flatMap { scanner1 -> position.map { scanner2 -> scanner1 to scanner2 } } }
        .map { (point1, point2) -> abs(point1.x - point2.x) + abs(point1.y - point2.y) + abs(point1.z - point2.z) }
        .maxOf { distance -> distance }

    private fun parse() = input.split(System.lineSeparator().repeat(2))
        .map { data -> data.split(System.lineSeparator()).drop(1).map { line -> line.split(",") } }
        .mapIndexed { index, data -> Scanner(index, data.map { (x, y, z) -> Point(x.toInt(), y.toInt(), z.toInt()) }) }

    fun solve(): List<Scanner> {
        val inputScanners = parse()

        val scannersToCheck = mutableListOf(inputScanners[0])
        val solvedScanners = mutableListOf(inputScanners[0])

        while (scannersToCheck.isNotEmpty()) {
            val scannerToCheck = scannersToCheck.removeFirst()

            val scannersToMatch = inputScanners
                .filter { scanner -> scanner.index !in solvedScanners.map { solvedScanner -> solvedScanner.index } }

            for (scanner in scannersToMatch) {
                matchScanner(scanner, scannerToCheck)?.let { matchedScanner ->
                    scannersToCheck += matchedScanner
                    solvedScanners += matchedScanner
                }
            }
        }

        return solvedScanners
    }

    private fun matchScanner(scanner: Scanner, solvedScanner: Scanner): Scanner? {
        for (swap in swaps) {
            for (negation in negations) {
                val adjustedBeacons = scanner.beacons.map { beacon -> swap(beacon) * negation }
                matchBeacons(adjustedBeacons, solvedScanner.beacons)?.let { delta ->
                    return Scanner(scanner.index, adjustedBeacons.map { beacon -> beacon - delta }, delta)
                }
            }
        }
        return null
    }

    private fun matchBeacons(beacons: List<Point>, solvedBeacons: List<Point>): Point? {
        for (beacon in beacons) {
            for (solvedBeacon in solvedBeacons) {
                val delta = beacon - solvedBeacon
                val count = beacons.count { beaconToAdjust -> beaconToAdjust - delta in solvedBeacons }
                if (count >= 12) {
                    return delta
                }
            }
        }
        return null
    }

    private val negations = listOf(-1, 1)
        .let { rotation -> rotation.flatMap { x -> rotation.flatMap { y -> rotation.map { z -> Point(x, y, z) } } } }

    private val swaps = listOf<(Point) -> Point>(
        { point -> Point(point.x, point.y, point.z) },
        { point -> Point(point.x, point.z, point.y) },
        { point -> Point(point.y, point.x, point.z) },
        { point -> Point(point.y, point.z, point.x) },
        { point -> Point(point.z, point.x, point.y) },
        { point -> Point(point.z, point.y, point.x) },
    )

    data class Scanner(val index: Int, val beacons: List<Point>, val position: Point = Point(0, 0, 0))

    data class Point(val x: Int, val y: Int, val z: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y, z + other.z)
        operator fun minus(other: Point) = Point(x - other.x, y - other.y, z - other.z)
        operator fun times(other: Point) = Point(x * other.x, y * other.y, z * other.z)
    }
}