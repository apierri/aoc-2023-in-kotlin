fun main() {
    val numberMap = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
        "1" to 1,
        "2" to 2,
        "3" to 3,
        "4" to 4,
        "5" to 5,
        "6" to 6,
        "7" to 7,
        "8" to 8,
        "9" to 9,
    )

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val firstDigit = line.first { it.isDigit() }
            val lastDigit = line.last { it.isDigit() }
            "$firstDigit$lastDigit".toInt()
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val firstDigit = line.findAnyOf(numberMap.keys, ignoreCase = false)!!.second.let { numberMap[it] }
            val lastDigit = line.findLastAnyOf(numberMap.keys, ignoreCase = false)!!.second.let { numberMap[it] }
            "$firstDigit$lastDigit".toInt()
        }
    }

    val input = readInput("Day01")
//    "Part 1 is ${part1(input)}".println()
//    "Part 2 is ${part2(input)}".println()
}
