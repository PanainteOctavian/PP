package com.pp.laborator

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

fun downloadWebPage(url: String): String
{
    val doc = Jsoup.connect(url).get()
    val html = doc.html()
    return html
}

fun parseDOM(content: String): Element
{
    val doc = Jsoup.parse(content)
    return doc.child(0)
}

fun printDOMTree(node : Element, depth : Int)
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
    for(child in node.children())
    {
        printDOMTree(child, depth + 1)
    }
}

@OptIn(ObsoleteCoroutinesApi::class)
fun CoroutineScope.webPageDownloaderActor() = actor<String>
{
    for (url : String in channel)
    {
        val content = downloadWebPage(url)
        val parser = domTreeParserActor()
        parser.send(content)
        parser.close()
    }
}

@OptIn(ObsoleteCoroutinesApi::class)
fun CoroutineScope.domTreeParserActor() = actor<String>
{
    for (content : String in channel)
    {
        val domTree = parseDOM(content)
        val printer = domTreePrinterActor()
        printer.send(domTree)
        printer.close()
    }
}

@OptIn(ObsoleteCoroutinesApi::class)
fun CoroutineScope.domTreePrinterActor() = actor<Element>
{
    for (domTree : Element in channel)
    {
        printDOMTree(domTree, 0)
    }
}

fun main() = runBlocking {
    print("Dati URL-ul site-ului la care doriti sa va conectati : ")
    val url = readlnOrNull()
    if (url != null)
    {
        val downloader = webPageDownloaderActor()
        downloader.send(url)
        downloader.close()
    }
}
