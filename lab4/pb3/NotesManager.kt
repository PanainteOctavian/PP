import java.io.File

class NotesManager(private val notesDirectory: String) {
    init {
        File(notesDirectory).mkdirs() // creeaza directorul pt notite
    }

    fun displayNotes() {
        val files = File(notesDirectory).listFiles()
        if (files.isNullOrEmpty()) {
            println("Nu exista notite.")
        }
        else {
            println("Notite: ")
            files.forEachIndexed { index, file -> println("${index + 1}. ${file.nameWithoutExtension}") } // index de la 1 pt utilizator
        }
    }

    fun loadNote(index: Int): Note? { // returneaza Note sau null
        val files = File(notesDirectory).listFiles()
        if (files.isNullOrEmpty() || index < 0 || index >= files.size) {
            println("Index invalid.")
            return null
        }
        val file = files[index]
        val lines = file.readLines()
        val author = lines[0]
        val date = lines[1]
        val time = lines[2]
        val content = lines.drop(3).joinToString("\n")
        return Note(author, date, time, content)
    }

    fun createNote(note: Note) {
        val fileName = "${note.author}_${note.date}_${note.time}.txt" // concatenare nume fisier
        val file = File("$notesDirectory/$fileName") // cale fisier
        file.writeText("${note.author}\n${note.date}\n${note.time}\n${note.content}") // scrie notitia in fisier
        println("Notita creata cu succes.")
    }

    fun deleteNote(index: Int) {
        val files = File(notesDirectory).listFiles()
        if (files.isNullOrEmpty() || index < 0 || index >= files.size) {
            println("Index invalid.")
            return
        }
        val file = files[index]
        if (file.delete()) {
            println("Notita adaugate cu succes.")
        }
        else {
            println("Nu s-a putut sterge notita.")
        }
    }
}
