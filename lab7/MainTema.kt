import java.io.File
import java.text.SimpleDateFormat
import java.util.*

data class HistoryLogRecord(
    val timestamp: Long, // calculata pe baza start-date
    val commandLine: String
) : Comparable<HistoryLogRecord> { // implementeaza interfata care compara
    override fun compareTo(other: HistoryLogRecord): Int {
        return this.timestamp.compareTo(other.timestamp) // met din long compareTo 
    }
}

fun parseDateToTimestamp(dateStr: String): Long { // string data -> timestamp
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return try {
        val date = format.parse(dateStr)
        date.time
    } catch (e: Exception) {
        0L // 0 Long
    }
}

fun processHistoryLog(filePath: String): MutableMap<Long, HistoryLogRecord> { // mapa cu cheie de tip Long, iar valoarea de tip history log record
    val historyMap = mutableMapOf<Long, HistoryLogRecord>()
    val lines = File(filePath).readLines().takeLast(50)

    var currentStartDate: String? = null
    var currentCommandLine: String? = null

    for (line in lines) { // parcurge fiecare linie si extrage datele relevante
        when {
            line.startsWith("Start-Date: ") -> { // extrage StartDate
                currentStartDate = line.removePrefix("Start-Date: ").trim()
            }
            line.startsWith("Commandline: ") -> { // extrage CommandLine
                currentCommandLine = line.removePrefix("Commandline: ").trim()
            }
            line.startsWith("End-Date: ") -> { // cand gaseste o inregistrare completa, o adauga in map
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

// fct generica care mi gaseste max a 2 obiecte comparabile
fun <T : Comparable<T>> findMax(a: T, b: T): T {
    return if (a > b) a else b
}

// fct generica care cauta si inlocuieste prin covarianta(out)
fun <T> searchAndReplace(searchFor: T, replaceWith: T, collection: MutableMap<out Any, T>) {
    for ((key, value) in collection) {
        if (value == searchFor) {
            (collection as MutableMap<Any, T>)[key] = replaceWith
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

    // findMax
    if (historyMap.size >= 2) {
        val records = historyMap.values.toList()
        val maxRecord = findMax(records[0], records[1])
        println("\nPrimele 2 intrari:")
        println("1. ${Date(records[0].timestamp)} - ${records[0].commandLine}")
        println("2. ${Date(records[1].timestamp)} - ${records[1].commandLine}")
        println("Cea cu timestamp mai mare: ${Date(maxRecord.timestamp)} - ${maxRecord.commandLine}")
    }

    // searchAndReplace
    if (historyMap.isNotEmpty()) {
        val firstRecord = historyMap.values.first()
        val newRecord = HistoryLogRecord(System.currentTimeMillis(), "COMANDA MODIFICATA")

        println("\nInainte sa modific prima comanda:")
        historyMap.forEach { (t, r) -> println("${Date(t)} - $r") }

        searchAndReplace(firstRecord, newRecord, historyMap)

        println("\nDupa:")
        historyMap.forEach { (t, r) -> println("${Date(t)} - $r") }
    }
}
