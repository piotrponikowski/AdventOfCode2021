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

            val scannersNotSolved = inputScanners
                .filter { scanner -> scanner.index !in solvedScanners.map { solvedScanner -> solvedScanner.index } }

            for (scanner in scannersNotSolved) {
                solveScanner(scanner, scannerToCheck)?.let { solvedScanner ->
                    scannersToCheck += solvedScanner
                    solvedScanners += solvedScanner
                }
            }
        }

        return solvedScanners
    }

    private fun solveScanner(scanner: Scanner, solvedScanner: Scanner): Scanner? {
        for (rotate in rotations) {
            val adjustedBeacons = scanner.beacons.map { beacon -> rotate(beacon) }
            findDelta(adjustedBeacons, solvedScanner.beacons)?.let { delta ->
                return Scanner(scanner.index, adjustedBeacons.map { beacon -> beacon - delta }, delta)
            }
        }
        return null
    }

    private fun findDelta(beacons: List<Point>, solvedBeacons: List<Point>): Point? {
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

    private val rotations = listOf<(Point) -> Point>(
        { (x, y, z) -> Point(x, -z, y) },
        { (x, y, z) -> Point(-y, -z, x) },
        { (x, y, z) -> Point(z, -y, x) },
        { (x, y, z) -> Point(y, z, x) },
        { (x, y, z) -> Point(-z, y, x) },
        { (x, y, z) -> Point(-x, -z, -y) },
        { (x, y, z) -> Point(z, -x, -y) },
        { (x, y, z) -> Point(x, z, -y) },
        { (x, y, z) -> Point(-z, x, -y) },
        { (x, y, z) -> Point(y, -z, -x) },
        { (x, y, z) -> Point(z, y, -x) },
        { (x, y, z) -> Point(-y, z, -x) },
        { (x, y, z) -> Point(-z, -y, -x) },
        { (x, y, z) -> Point(z, x, y) },
        { (x, y, z) -> Point(-x, z, y) },
        { (x, y, z) -> Point(-z, -x, y) },
        { (x, y, z) -> Point(x, -y, -z) },
        { (x, y, z) -> Point(-x, -y, z) },
        { (x, y, z) -> Point(y, -x, z) },
        { (x, y, z) -> Point(x, y, z) },
        { (x, y, z) -> Point(-y, x, z) },
        { (x, y, z) -> Point(y, x, -z) },
        { (x, y, z) -> Point(-x, y, -z) },
        { (x, y, z) -> Point(-y, -x, -z) },
    )
    
    data class Scanner(val index: Int, val beacons: List<Point>, val position: Point = Point(0, 0, 0))

    data class Point(val x: Int, val y: Int, val z: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y, z + other.z)
        operator fun minus(other: Point) = Point(x - other.x, y - other.y, z - other.z)
    }
}
