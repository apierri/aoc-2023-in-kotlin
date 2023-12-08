fun main() {

    fun part1(input: List<String>): Int {
        val instructions = input[0].asIterable().iterateInfinitely()
        val nodes = input.drop(2).associate { line ->
            line.split(Regex("[^A-Z]+")).let {
                it[0] to Pair(it[1], it[2])
            }
        }
        var currentNode = "AAA"
        return instructions.takeWhile { instruction ->
            currentNode = nodes[currentNode]!!.let {
                if(instruction == 'L') {
                    it.first
                } else {
                    it.second
                }
            }
            currentNode != "ZZZ"
        }.count() + 1
    }

    class CycleCounter(
        var initialSolution: String?,
        var currentValue: String,
        var length: Long,
        var solved: Boolean
    )

    fun gcd(a: Long, b: Long): Long {
        if (b == 0L) return a
        return gcd(b, a % b)
    }

    fun lcm(a: Long, b: Long): Long {
        return a / gcd(a, b) * b
    }

    fun lcmOfList(numbers: List<Long>): Long {
        return numbers.fold(1L) { lcm, number -> lcm(lcm, number) }
    }

    fun part2(input: List<String>): Long {
        val instructions = input[0].asIterable().iterateInfinitely()
        val nodes = input.drop(2).associate { line ->
            line.split(Regex("[^0-9A-Z]+")).let {
                it[0] to Pair(it[1], it[2])
            }
        }
        val cycleCounters = nodes.keys.filter { it.last() == 'A' }.map {
            CycleCounter(
                initialSolution = null,
                currentValue = it,
                length = 0,
                solved = false
            )
        }
        instructions.takeWhile { instruction ->
            cycleCounters.filterNot { it.solved }.forEach {  cc ->
                cc.currentValue = nodes[cc.currentValue]!!.let {
                    if(instruction == 'L') {
                        it.first
                    } else {
                        it.second
                    }
                }
                cc.initialSolution?.let {
                    cc.length += 1
                    if(cc.currentValue == cc.initialSolution) {
                        cc.solved = true
                    }
                } ?: if(cc.currentValue.endsWith('Z')) {
                    cc.initialSolution = cc.currentValue
                } else {}
            }
            cycleCounters.any { !it.solved }
        }.count()
        return lcmOfList(cycleCounters.map { it.length })
    }

    val input = readInput("Day08")
    "Part 1 is ${part1(input)}".println()
    "Part 2 is ${part2(input)}".println()
}