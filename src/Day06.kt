fun main() {

    fun parseRecords(input: List<String>): Map<Int, Int> {
        return input.map { line ->
            line.split(Regex("\\s+")).drop(1).map { it.toInt() }
        }.let {
            it[0].zip(it[1])
        }.toMap()
    }

    fun parseWithoutKerning(input: List<String>): Pair<Long, Long> {
        return input.map { line ->
            line.split(Regex("\\s+"), limit = 2)[1].filterNot { it == ' ' }.toLong()
        }.let {
            it[0] to it[1]
        }
    }

    fun part1(input: List<String>): Int {
        return parseRecords(input).map { (time, record) ->
            var ways = (1..(time/2)).count {
                (it * (time-it)) > record
            } * 2
            if(time % 2 == 0 && ways > 0) {
                ways -= 1
            }
            ways
        }.fold(1) { acc, num -> acc*num }
    }

    fun part2(input: List<String>): Long {
        val (time, distance) = parseWithoutKerning(input)
        println("TIME: $time, DISTANCE: $distance")
        var start = 1
        while (start * (time-start) <= distance){
            start++
        }
        start--
        return time - (start * 2) - 1
    }

    val input = readInput("Day06")
    "Part 1 is ${part1(input)}".println()
    "Part 2 is ${part2(input)}".println()
}