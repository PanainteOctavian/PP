package com.pp.laborator

import kotlinx.coroutines.*
import java.io.File

fun main() = runBlocking {
    val files = listOf("file1.txt", "file2.txt", "file3.txt", "file4.txt")

    files.forEach { fileName ->
        launch(Dispatchers.IO) {
            val file = File(fileName)
            if (file.exists())
            {
                val content = file.readText()
                println("Fișierul $fileName conține: $content")
            }
            else
            {
                println("Fișierul $fileName nu există.")
            }
        }
    }
}
