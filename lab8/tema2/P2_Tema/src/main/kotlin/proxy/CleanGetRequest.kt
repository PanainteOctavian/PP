package proxy

import GetRequest
import HTTPGet
import okhttp3.Response

class CleanGetRequest(private val getRequest: GetRequest) : HTTPGet {
    private val parentalControlDissalow : List<String> = listOf(
        "https://www.google.com/",
        "site2.ro",
        "site3.tv"
    )

    override fun getResponse(): Response? {
        if(parentalControlDissalow.contains(getRequest.getGenericRequest().url.substringAfter("/").drop(1).substringBefore("/").substringAfter("www."))) {
            throw Exception("Site-ul nu este acceptat de controlul parental!")
        }
        return getRequest.getResponse()
    }

    fun getURL() : String {
        return getRequest.getGenericRequest().url
    }
}