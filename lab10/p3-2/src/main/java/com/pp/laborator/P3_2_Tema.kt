package com.pp.laborator

import java.util.concurrent.LinkedBlockingQueue

fun main()
{
    val queue = LinkedBlockingQueue<Int>()

    queue.add(10)
    queue.add(20)
    queue.add(30)
    queue.add(40)

    val threads = mutableListOf<Thread>()

    for(i in 1..4)
    {
        val thread = Thread{
            try
            {
                val n = queue.take()
                val sum = (0..n).sum()
                println("Suma pentru n = $n este $sum")
            }
            catch(e : Exception)
            {
                println("A aparut o eroare : ${e.message}")
            }
        }
        threads.add(thread)
        thread.start()
    }

    threads.forEach{ it.join() }
}