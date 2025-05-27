import java.io.File

fun caesarCipherEncrypt(text : String, offset : Int) : String
{
    return text.map { char ->
        when
        {
            char.isUpperCase() -> ((char - 'A' + offset) % 26 + 'A'.code).toChar()
            char.isLowerCase() -> ((char - 'a' + offset) % 26 + 'a'.code).toChar()
            else -> char
        }
    }.joinToString("")
}

fun processFile(filePath : String, offset : Int) : String
{
    val file = File(filePath)
    val words = file.readText().split("\\s+".toRegex())

    return words.joinToString(" ") { word ->
        if (word.length in 4..7) caesarCipherEncrypt(word, offset) else word
    }
}

fun main()
{
    print("Dati calea : ")
    val filePath = readlnOrNull()!!

    print("Dati offset-ul : ")
    val offset = readlnOrNull()!!.toIntOrNull()!!

    println("Fisierul procesat : ${processFile(filePath, offset)}")
}