import kotlin.math.pow

fun main() {
	data class Game(
		val winningNumbers: Sequence<Int>,
		val myNumbers: Sequence<Int>,
	)

	fun stringToNumbers(str: String): Sequence<Int> = str.trim().lazySplit(' ').filter{ it.isNotEmpty() }.map { it.toInt() }

	fun parseGame(line: String): Game {
		return line.split(':', limit = 2)[1].split('|', limit = 2).let {
			Game(stringToNumbers(it[0]), stringToNumbers(it[1]))
		}
	}

	fun part1(input: List<String>): Int{
		var points = 0
		input.forEach { line ->
			val game = parseGame(line)
			val wins = game.myNumbers
				.filter { game.winningNumbers.any { winNumber -> it == winNumber  } }
				.count()
			if(wins != 0) {
				points += (2.0).pow(wins - 1).toInt()
			}
		}
		return points
	}

	fun part2(input: List<String>): Int {
		val copies = mutableMapOf<Int, Int>()
		input.forEachIndexed { cardNumber, line ->
			val game = parseGame(line)
			val wins = game.myNumbers
				.filter { game.winningNumbers.any { winNumber -> it == winNumber  } }
				.count()
			(cardNumber+1..cardNumber+wins).forEach { index ->
				copies[index] = (copies[index] ?: 0) + (copies[cardNumber] ?: 0) + 1 // original
			}
		}
		return copies.values.sum() + input.size
	}

	val input = readInput("Day04")
	"Part 1 is ${part1(input)}".println()
	"Part 2 is ${part2(input)}".println()
}
