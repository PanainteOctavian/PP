package com.pp.laborator

import java.io.File
import java.util.concurrent.Executors

fun main()
{
    val files = listOf("file1.txt", "file2.txt", "file3.txt", "file4.txt")

    val executor = Executors.newFixedThreadPool(files.size)

    files.forEach { fileName ->
        executor.execute{
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

    executor.shutdown()
    while (!executor.isTerminated)
    {
        //Se asteapta executia thread-urilor
    }
}