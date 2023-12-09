fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            var numbers = line.lazySplit(' ').map { it.toInt() }.toList()
            val lastNumbers = mutableListOf<Int>()
            while(!numbers.all { it == 0 }) {
                lastNumbers += numbers.last()
                numbers = numbers.zipWithNext().map { it.second - it.first }
            }
            lastNumbers.sum()
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            var numbers = line.lazySplit(' ').map { it.toInt() }.toList()
            val lastNumbers = mutableListOf<Int>()
            while(!numbers.all { it == 0 }) {
                lastNumbers += numbers.first()
                numbers = numbers.zipWithNext().map { it.second - it.first }
            }
            lastNumbers.foldRight(0) { it, acc ->
                it - acc
            }.toInt()
        }
    }

    val input = readInput("Day09")
    "Part 1 is ${part1(input)}".println()
    "Part 2 is ${part2(input)}".println()
}