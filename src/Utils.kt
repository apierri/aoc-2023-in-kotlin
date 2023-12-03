import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun String.lazySplit(delimiter: Char): Sequence<String> = sequence {
    var currentIndex = 0
    while (currentIndex < length) {
        val nextIndex = indexOf(delimiter, currentIndex).takeIf { it != -1 } ?: length
        yield(substring(currentIndex, nextIndex))
        currentIndex = nextIndex + 1
    }
}

fun String.lazySplitIndexed(delimiter: Char): Sequence<Pair<Int, String>> = sequence {
    var currentIndex = 0
    while (currentIndex < length) {
        val nextIndex = indexOf(delimiter, currentIndex).takeIf { it != -1 } ?: length
        yield(currentIndex to substring(currentIndex, nextIndex))
        currentIndex = nextIndex + 1
    }
}
