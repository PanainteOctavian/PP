import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class User(private val noteManager: NotesManager) {
    fun displayNotes() {
        noteManager.displayNotes()
    }

    fun loadNote() {
        print("Dati indexul notitei pe care doriti sa o incarcati: ")
        val index = readlnOrNull()?.toIntOrNull()?.minus(1) ?: return
        val note = noteManager.loadNote(index)
        println(note?.let { "Loaded Note: $it" } ?: "Invalid index.")
    }

    fun createNote() {
        print("Dati autorul: ")
        val author = readlnOrNull() ?: return
        val currentDateTime = LocalDateTime.now()
        val date = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val time = currentDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
        print("Dati continutul: ")
        val content = readlnOrNull() ?: return
        val note = Note(author, date, time, content)
        noteManager.createNote(note)
    }

    fun deleteNote() {
        print("Dati indexul notitei pe care doriti sa o stergeti: ")
        val index = readlnOrNull()?.toIntOrNull()?.minus(1) ?: return
        noteManager.deleteNote(index)
    }
}