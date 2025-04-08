import java.io.File
import java.text.SimpleDateFormat
import java.util.*

data class HistoryLogRecord(
    val timestamp: Long, // calculata pe baza start-date
    val commandLine: String
) : Comparable<HistoryLogRecord> { // implementeaza interfata Comparable
    override fun compareTo(other: HistoryLogRecord): Int {
        return this.timestamp.compareTo(other.timestamp)
    }
}

// data -> timestamp
fun parseDateToTimestamp(dateStr: String): Long {
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return try {
        val date = format.parse(dateStr)
        date.time
    } catch (e: Exception) {
        0L //
    }
}

// proceseaza ultimele 50 de intrari ale history.log
fun processHistoryLog(filePath: String): MutableMap<Long, HistoryLogRecord> {
    val historyMap = mutableMapOf<Long, HistoryLogRecord>()
    val lines = File(filePath).readLines().takeLast(50)

    var currentStartDate: String? = null
    var currentCommandLine: String? = null

    for (line in lines) {
        when {
            line.startsWith("Start-Date: ") -> {
                currentStartDate = line.removePrefix("Start-Date: ").trim()
            }
            line.startsWith("Commandline: ") -> {
                currentCommandLine = line.removePrefix("Commandline: ").trim()
            }
            line.startsWith("End-Date: ") -> {
                if (currentStartDate != null && currentCommandLine != null) {
                    val timestamp = parseDateToTimestamp(currentStartDate)
                    historyMap[timestamp] = HistoryLogRecord(timestamp, currentCommandLine)
                }
                currentStartDate = null
                currentCommandLine = null
            }
        }
    }

    return historyMap
}

// fct generica care mi gaseste maximul a 2 obiecte Comparable
fun <T : Comparable<T>> findMax(a: T, b: T): T {
    return if (a > b) a else b
}

// fct generica care cauta si inlocuieste prin covarianta
fun <T> searchAndReplace(
    searchFor: T,
    replaceWith: T,
    collection: MutableMap<*, T>,
    where: (T, T) -> Boolean = { a, b -> a == b }
) where T : Any {
    for ((key, value) in collection) {
        if (where(value, searchFor)) {
            (collection as MutableMap<Any, T>)[key as Any] = replaceWith
        }
    }
}

fun main() {
    val historyMap = processHistoryLog("/var/log/apt/history.log")

    // afisare
    println("Intrari in history log:")
    historyMap.forEach { (timestamp, record) ->
        println("${Date(timestamp)} - ${record.commandLine}")
    }

    if (historyMap.size >= 2) {
        val records = historyMap.values.toList()
        val maxRecord = findMax(records[0], records[1])
        println("\nCele mai apropiate 2 intrari:")
        println("1. ${Date(records[0].timestamp)} - ${records[0].commandLine}")
        println("2. ${Date(records[1].timestamp)} - ${records[1].commandLine}")
        println("sunt: ${Date(maxRecord.timestamp)} - ${maxRecord.commandLine}")
    }

    if (historyMap.isNotEmpty()) {
        val firstRecord = historyMap.values.first()
        val newRecord = HistoryLogRecord(System.currentTimeMillis(), "COMANDA MODIFICATA")

        println("\nInainte:")
        historyMap.forEach { (t, r) -> println("${Date(t)} - $r") }

        searchAndReplace(firstRecord, newRecord, historyMap)

        println("\nDupa:")
        historyMap.forEach { (t, r) -> println("${Date(t)} - $r") }
    }
}