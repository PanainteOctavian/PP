package browser.models

import java.io.File

interface RequestPrototype : Cloneable {
    public override fun clone(): RequestPrototype
}

data class Request(
    val url: String,
    val method: String = "GET",
    val headers: Map<String, String> = emptyMap()
) : RequestPrototype {
    override fun clone(): RequestPrototype {
        return this.copy()
    }
}

data class Response(
    val statusCode: Int,
    val content: String,
    val headers: Map<String, String>
)

class ParentalControlProxy {
    private val blockedSites = mutableSetOf<String>()

    init {
        File("src/main/resources/blocked_sites.txt").useLines { lines ->
            blockedSites.addAll(lines.map { it.trim() })
        }
    }

    fun makeRequest(request: Request): Response {
        val domain = extractDomain(request.url)

        if (blockedSites.contains(domain)) {
            return Response(
                statusCode = 403,
                content = "Acces blocat de controlul parental",
                headers = emptyMap()
            )
        }

        return RealHttpRequest().execute(request)
    }

    private fun extractDomain(url: String): String {
        return url.replace(Regex("^(https?://)?(www\\.)?"), "")
            .split('/')[0]
            .lowercase()
    }
}

class RealHttpRequest {
    fun execute(request: Request): Response {
        return Response(
            statusCode = 200,
            content = "Conținutul site-ului ${request.url}",
            headers = emptyMap()
        )
    }
}

class BrowserFacade {
    private val proxy = ParentalControlProxy()

    fun navigateTo(url: String): Response {
        val request = Request(url = url)
        return proxy.makeRequest(request)
    }

    fun search(query: String): Response {
        val searchUrl = "https://safe-search.example.com?q=${query.replace(" ", "+")}"
        val request = Request(url = searchUrl)
        return proxy.makeRequest(request)
    }
}

class KidsBrowser {
    private val facade = BrowserFacade()

    fun open(url: String): String {
        val response = facade.navigateTo(url)
        return when (response.statusCode) {
            200 -> response.content
            403 -> "Eroare: ${url} -> ${response.content}"
            else -> "Eroare necunoscută"
        }
    }

    fun search(query: String): String {
        val response = facade.search(query)
        return when (response.statusCode) {
            200 -> "Rezultate căutare:\n${response.content}"
            else -> "Nu s-au putut obține rezultatele căutării"
        }
    }
}

fun main() {
    val browser = KidsBrowser()

    println("Navigare")
    println(browser.open("https://www.example.com"))
    println(browser.open("https://www.blocked-site.com"))
    println(browser.open("https://adult-site.example"))

    println("\nCăutare")
    println(browser.search("site ok"))
}
