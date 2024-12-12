import java.math.BigInteger
import java.net.HttpURLConnection
import java.net.URL
import java.nio.file.Path
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.createFile
import kotlin.io.path.exists
import kotlin.io.path.outputStream
import kotlin.io.path.readLines
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/input/$name.txt").readLines()

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

fun readTestInput(year: Int, day: Int, postFix: String = ""): List<String> {
    val inputFile = inputFile(year, day, "-test$postFix")
    return if (inputFile.exists()) {
        inputFile.readLines()
    } else {
        inputFile.createFile()
        throw NoSuchElementException("$inputFile (empty file created)")
    }
}

fun inputFile(year: Int, day: Int, postFix: String = ""): Path = Path(buildString {
    append("src/input/Day")
    append(year)
    append('-')
    if (day < 10) append(0)
    append(day)
    append(postFix)
    append(".txt")
})

fun readInput(year: Int, day: Int): List<String> {
    val file = inputFile(year, day)
    if (!file.exists()) {
        val session = Path("src/Cookie.txt").readText().trim()
        val connection = URL("https://adventofcode.com/$year/day/$day/input").openConnection() as HttpURLConnection
        connection.setRequestProperty("Accept-language", "en-US,en;q=0.9")
        connection.setRequestProperty("Cookie", "session=$session")
        if (connection.responseCode in 200..299) {
            connection.inputStream.transferTo(file.outputStream())
        } else {
            val errorString = connection.errorStream.readAllBytes().toString(Charsets.UTF_8)
            throw RuntimeException("failed to download -> $errorString")
        }
    }
    return file.readLines()
}
