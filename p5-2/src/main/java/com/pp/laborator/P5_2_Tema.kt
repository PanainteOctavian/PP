package com.pp.laborator

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue

fun downloadWebPageF(url: String): String
{
    val doc = Jsoup.connect(url).get()
    val html = doc.html()
    return html
}

fun parseDOMF(content: String): Element
{
    val doc = Jsoup.parse(content)
    return doc.child(0)
}

fun printDOMTreeF(node: Element, depth: Int)
{
    val text = node.ownText()
    if (text.isNotEmpty())
    {
        println("${"\t".repeat(depth)}${node.tagName()}: $text")
    }
    else
    {
        println("${"\t".repeat(depth)}${node.tagName()}")
    }
    for (child in node.children())
    {
        printDOMTreeF(child, depth + 1)
    }
}

fun main()
{
    val executorService = Executors.newFixedThreadPool(3)

    val downloadQueue = LinkedBlockingQueue<String>()
    val parseQueue = LinkedBlockingQueue<String>()
    val printQueue = LinkedBlockingQueue<Element>()

    executorService.execute {
        val url = downloadQueue.take()
        val content = downloadWebPageF(url)
        parseQueue.put(content)
    }

    executorService.execute {
        val content = parseQueue.take()
        val domTree = parseDOMF(content)
        printQueue.put(domTree)
    }

    executorService.execute {
        val domTree = printQueue.take()
        printDOMTreeF(domTree, 0)
    }

    print("Dati URL-ul site-ului la care doriti sa va conectati : ")
    val url = readlnOrNull()
    if (url != null)
    {
        downloadQueue.put(url)
    }

    executorService.shutdown()
    while (!executorService.isTerminated)
    {
        //Se asteapta executia thread-urilor
    }
}