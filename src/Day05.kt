@file:Suppress("ReplaceRangeStartEndInclusiveWithFirstLast")

fun main() {

	data class Mapper(
		val from: Long,
		val to: Long,
		val delta: Long
	) {
		fun range() = from..to
	}

	data class AlmanacMapPhase(
		val mappers: List<Mapper>,
	)

	data class Almanac(
		val phases: List<AlmanacMapPhase>,
	) {
		fun getDestination(seed: Long) = phases.fold(seed) { intermediateValue, phase ->
			phase.mappers.firstOrNull { it.range().contains(intermediateValue) }?.let {
				intermediateValue + it.delta
			} ?: intermediateValue
		}
	}

	fun parseAlmanac(input: List<String>): Almanac {
		val mappers = input
			.drop(2) // Seeds
			.dropLast(1) // Empty Line
			.splitBy { it.isBlank() }
			.map { phaseString ->
				phaseString
					.drop(1) // Irrelevant phase title
					.map { line ->
						val (destination, source, length) = line.split(' ').map { it.toLong() }
						Mapper(
							from = source,
							to = source + length - 1,
							delta = destination - source
						)
				}
			}.map { AlmanacMapPhase(it) }
		return Almanac(mappers)
	}

	fun part1(almanac: Almanac, seedLine: String): Long {
		val seeds = seedLine.lazySplit(' ').drop(1).map { it.toLong() }
		var lowestDestination = Long.MAX_VALUE
		seeds.forEach {
			lowestDestination = almanac.getDestination(it).coerceAtMost(lowestDestination)
		}
		return lowestDestination
	}

	fun LongRange.intersects(range: LongRange) = start <= range.endInclusive && range.start <= endInclusive

	fun LongRange.intersection(range: LongRange) = maxOf(start, range.start)..minOf(endInclusive, range.endInclusive)

	fun LongRange.substract(range: LongRange): List<LongRange> {
		return if(intersects(range)){
			listOf(
				start..<range.start,
				(range.endInclusive + 1)..endInclusive,
			).filterNot { it.isEmpty() }
		} else listOf(this)
	}

	fun part2(almanac: Almanac, seedLine: String): Long {
		val seedRanges = seedLine.lazySplit(' ').drop(1).chunked(2).map {
			it[0].toLong() ..< (it[0].toLong() + it[1].toLong())
		}
		return almanac.phases.fold(seedRanges.toList()) { initialSources, phase ->
			val destinationRanges = mutableListOf<LongRange>()
			phase.mappers.fold(initialSources) { sources, mapper ->
				sources.map { source ->
					if (mapper.range().intersects(source)) {
						(mapper.range()).intersection(source).let {
							destinationRanges += (it.start + mapper.delta)..(it.endInclusive + mapper.delta)
						}
						source.substract(mapper.range())
					} else listOf(source)
				}.flatten()
			}.plus(destinationRanges)
		}.minOf { it.start }
	}

	val input = readInput("Day05")
	val seedLine = input[0]
	val almanac = parseAlmanac(input)
	"Part 1 is ${part1(almanac, seedLine)}".println()
	"Part 2 is ${part2(almanac, seedLine)}".println()
}
