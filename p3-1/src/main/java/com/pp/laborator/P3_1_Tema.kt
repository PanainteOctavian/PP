package com.pp.laborator

import kotlinx.coroutines.*
import java.util.*

fun main() = runBlocking {
    val queue = LinkedList<Int>()
    queue.addAll(listOf(10, 20, 30, 40))

    val jobs = List(4)
    {
        launch {
            val n = queue.poll()
            val sum = (0..n).sum()
            println("Suma numerelor de la 0 la $n este $sum")
        }
    }

    jobs.joinAll()
}