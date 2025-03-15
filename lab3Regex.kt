import java.io.*

fun main() {
    val bufferedReader: BufferedReader = File("G:\\My Drive\\Materiale\\AN2\\SEM2\\PP\\L\\Lab meu\\lab3\\tema2\\src\\input.txt").bufferedReader()
    var string = bufferedReader.use { it.readText() }

    println("Exemplu:\n" + string)
    println("-".repeat(100))

    val spatiiMultiple = Regex("  ")
    println("Fara spatii multiple:\n${spatiiMultiple.replace(string, " ")}")
    println("-".repeat(100))

    val saltLaLinieNoua = Regex("\r\n")
    println("Fara salt la linie noua:\n${saltLaLinieNoua.replace(string, " ")}")
    println("-".repeat(100))

    val numarPagina = Regex("[0-9]")
    println("Fara numar pagina:\n${numarPagina.replace(string, "")}")
    println("-".repeat(100))

    string = string.replace(spatiiMultiple, " ")
    string = string.replace(saltLaLinieNoua, " ")
    string = string.replace(numarPagina, "")
    println(string)
}
