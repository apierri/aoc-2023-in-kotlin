data class PartNumber(
	val range: IntRange,
	val num: Int
)

fun getPartNumbers(input: String): List<PartNumber> {
	return Regex("(\\d+)").findAll(input).map {
		PartNumber(
			range = it.range,
			num = it.value.toInt()
		)
	}.toList()
}

fun IntRange.expandOne(): IntRange = start-1..endInclusive+1

fun getSymbols(line: String) = Regex("[^\\.\\d]").findAll(line).map { it.range.first }

fun main() {

	fun part1(input: List<String>): Int {
		var previousLine = sequence<Int> {}
		var currentLine = getSymbols(input.getOrNull(0) ?: "")
		var nextLine: Sequence<Int>
		return input.mapIndexed { index, line ->
			nextLine = getSymbols(input.getOrNull(index+1) ?: "")

			val nums = getPartNumbers(line).filter { partNumber ->
				listOf(previousLine, currentLine, nextLine).any { symbols ->
					symbols.any { symbol ->
						partNumber.range.expandOne().contains(symbol)
					}

				}
			}.sumOf { it.num }

			previousLine = currentLine
			currentLine = nextLine
			nums
		}.sum()
	}

	fun part2(input: List<String>): Long {
		var previousLine = emptyList<PartNumber>()
		var currentLine = getPartNumbers(input.getOrElse(0) { "" })
		var nextLine = emptyList<PartNumber>()
		return input.mapIndexed { index, line ->
			nextLine = getPartNumbers(input.getOrElse(index+1) { "" })

			val gearsSum = getSymbols(line).sumOf { symbol ->
				val adjacents = listOf(previousLine, currentLine, nextLine).flatten().filter {
					it.range.expandOne().contains(symbol)
				}
				return@sumOf if(adjacents.size == 2) {
					adjacents[0].num * adjacents[1].num
				} else { 0 }
			}.toLong()

			previousLine = currentLine
			currentLine = nextLine
			gearsSum
		}.sum()
	}

	val input = readInput("Day03")
	"Part 1 is ${part1(input)}".println()
	"Part 2 is ${part2(input)}".println()
}
