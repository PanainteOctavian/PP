import java.io.File

// inregistrare din syslog
data class SyslogRecord(
    val timestamp: String,  // data si ora ca "Oct 12 14:15:32"
    val hostname: String,   // numele calculatorului
    val application: String,// numele aplicatiei
    val pid: Int?,          // id proces (optional)
    val message: String     // mesajul propriu-zis
) {
    override fun toString(): String = "$timestamp $hostname $application${pid?.let { "[$it]" } ?: ""}: $message"
}

// parseaza o linie din syslog
fun parseSyslogLine(line: String): SyslogRecord? {
    val pattern = Regex("""^(\w{3}\s+\d{1,2} \d{2}:\d{2}:\d{2}) (\S+) (\S+?)(?:\[(\d+)])?:\s*(.*)$""")
    return pattern.matchEntire(line)?.let {
        SyslogRecord(it.groupValues[1], it.groupValues[2], it.groupValues[3], it.groupValues[4].toIntOrNull(), it.groupValues[5])
    }
}

// proceseaza intreg fisierul syslog
fun processSyslogFile(filePath: String): Map<String, List<SyslogRecord>> {
    return File(filePath).readLines()
        .mapNotNull { parseSyslogLine(it) }
        .groupBy { it.application }
        .mapValues { it.value.sortedBy { r -> r.message } }
}

// filtreaza inregistrarile care au pid
fun filterRecordsWithPid(records: Map<String, List<SyslogRecord>>): Map<String, List<SyslogRecord>> {
    return records.mapValues { entry ->
        entry.value.filter { it.pid != null }
    }.filter { it.value.isNotEmpty() }
}

fun main() {
    val syslogData = processSyslogFile("syslog")

    // afiseaza statistici
    println("statistici syslog:")
    syslogData.forEach { (app, records) ->
        println("$app: ${records.size} inregistrari")
    }

    // afiseaza primele 3 inregistrari pentru fiecare aplicatie
    println("\nprimele 3 inregistrari pentru fiecare aplicatie:")
    syslogData.forEach { (app, records) ->
        println("\n--- $app (${records.size} total) ---")
        records.take(3).forEach { println(it) }
    }

    // afiseaza doar inregistrarile cu pid
    val recordsWithPid = filterRecordsWithPid(syslogData)
    println("\ninregistrari cu pid specificat:")
    recordsWithPid.forEach { (app, records) ->
        println("\n--- $app (${records.size} cu pid) ---")
        records.forEach { println(it) }
    }
}
