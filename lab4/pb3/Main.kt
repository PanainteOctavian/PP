fun main() {
    val folder = "Notite"
    val noteManager = NotesManager(folder)
    val user = User(noteManager)

    var option: Int
    while(true) {
        println("Optiuni:")
        println("1. Afisare lista de notite")
        println("2. Incarcare notita")
        println("3. Creare notita")
        println("4. Stergere notita")
        println("0. Iesire")
        print("Optiune: ")
        option = readlnOrNull()?.toIntOrNull()!!
        when (option) {
            1 -> user.displayNotes()
            2 -> user.loadNote()
            3 -> user.createNote()
            4 -> user.deleteNote()
            0 -> {
                println("Iesire aplicatie")
                return
            }
            else -> println("Optiune invalida.")
        }
        println("-".repeat(30))
    }
}