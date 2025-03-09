fun main() {
    var string =    "A fost odata  ca-n povesti\n" + "A fost ca niciodata\n" + "\t\t\t\t\t\t 1\n" +
                    "Din rude mari  imparatesti\n" + "O prea frumoasa fata\n" + "\t\t\t\t\t\t 2"
    println("Exemplu:\n" + string)
    println("-".repeat(100))

    val spatiiMultiple = Regex("  ")
    println("Fara spatii multiple:\n${spatiiMultiple.replace(string, " ")}")
    println("-".repeat(100))

    val saltLaLinieNoua = Regex("\n")
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