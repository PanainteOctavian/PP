package com.pp.laborator

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*


@OptIn(ExperimentalCoroutinesApi::class)
fun CoroutineScope.multiplyByAlpha(v : MutableList<Int>, alpha : Int) = produce{
    val result = v.map{ it * alpha }.toMutableList()
    send(result)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun CoroutineScope.sortVector(vCh : ReceiveChannel<MutableList<Int>>) = produce{
    vCh.consumeEach { vector ->
        val sortedVector = vector.sorted().toMutableList()
        send(sortedVector)
    }
}

fun CoroutineScope.printVector(vCh: ReceiveChannel<MutableList<Int>>)
{
    launch{ vCh.consumeEach { vector -> println("Vectorul final este: $vector") } }
}

fun main() = runBlocking{
    val v = mutableListOf(3, 4, 5, 9, 1, 2, 5, 3, 7, 0, 6)

    val alpha = 3

    val multipliedJob = multiplyByAlpha(v, alpha)
    val sortedJob = sortVector(multipliedJob)
    printVector(sortedJob)

}