enum class CubeColor(val restriction: Int) {
	RED(12), GREEN(13), BLUE(14);

	companion object {
		fun ofString(str: String) = entries.first { it.name == str.uppercase() }
	}
}

typealias CubeColorPull = Pair<CubeColor, Int>

fun main() {
	val gameLength = "Game ".length
	val firstSpace = 1

	fun CubeColorPull.isImpossible() = second > first.restriction

	data class Game (
		val id: Int,
		val sets: Sequence<Sequence<CubeColorPull>> // Sequence of sets
	)

	fun parseGame(line: String): Game {
		return line.split(':', limit = 2).let { gameString ->
			Game(
				id = gameString.first().drop(gameLength).toInt(),
				sets = gameString.last().lazySplit(';').map { set ->
					set.lazySplit(',').map { pull ->
						pull.drop(firstSpace).split(' ').let {
							CubeColor.ofString(it.last()) to it.first().toInt()
						}
					}
				}
			)
		}
	}

	fun part1(input: List<String>): Int {
		return input.sumOf { line ->
			val game = parseGame(line)
			return@sumOf if(game.sets.any { set -> set.any { pull -> pull.isImpossible() } }){
				0
			} else { game.id }
		}
	}

	fun part2(input: List<String>): Long {
		return input.sumOf { line ->
			val game = parseGame(line)
			val amounts = CubeColor.entries.associateWith { 0 }.toMutableMap()
			game.sets.forEach { pull ->
				pull.forEach {
					amounts[it.first] = maxOf(it.second, amounts[it.first]!!)
				}
			}
			return@sumOf amounts.values.fold(1) { val1, val2 -> val1*val2 }.toLong()
		}
	}

	val input = readInput("Day02")
	"Part 1 is ${part1(input)}".println()
	"Part 2 is ${part2(input)}".println()
}
