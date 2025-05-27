package com.pp.laborator

import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

class VectorMultiplier(private val inputVector: MutableList<Int>, private val alpha: Int, private val outputQueue: BlockingQueue<MutableList<Int>>) : Runnable
{
    override fun run()
    {
        val result = mutableListOf<Int>()
        for (value in inputVector)
        {
            result.add(value * alpha)
        }
        try
        {
            outputQueue.put(result)
        }
        catch (e: InterruptedException)
        {
            Thread.currentThread().interrupt()
        }
    }
}

class VectorSorter(private val inputQueue: BlockingQueue<MutableList<Int>>, private val outputQueue: BlockingQueue<MutableList<Int>>) : Runnable
{
    override fun run()
    {
        try
        {
            val vector = inputQueue.take()
            vector.sort()
            outputQueue.put(vector)
        }
        catch (e: InterruptedException)
        {
            Thread.currentThread().interrupt()
        }
    }
}

class VectorPrinter(private val inputQueue: BlockingQueue<MutableList<Int>>) : Runnable
{
    override fun run()
    {
        try
        {
            val sortedVector = inputQueue.take()
            println("Final vector is: $sortedVector")
        }
        catch (e: InterruptedException)
        {
            Thread.currentThread().interrupt()
        }
    }
}

fun main()
{
    val v = mutableListOf(3, 4, 5, 9, 1, 2, 5, 3, 7, 0, 6)
    val alpha = 3

    val multipliedQueue: BlockingQueue<MutableList<Int>> = LinkedBlockingQueue()
    val sortedQueue: BlockingQueue<MutableList<Int>> = LinkedBlockingQueue()

    val multiplierThread = Thread(VectorMultiplier(v, alpha, multipliedQueue))
    val sorterThread = Thread(VectorSorter(multipliedQueue, sortedQueue))
    val printerThread = Thread(VectorPrinter(sortedQueue))

    multiplierThread.start()
    sorterThread.start()
    printerThread.start()

    try
    {
        multiplierThread.join()
        sorterThread.join()
        printerThread.join()
    }
    catch (e: InterruptedException)
    {
        Thread.currentThread().interrupt()
    }
}
