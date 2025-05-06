import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import prototype.GenericRequest
import java.util.concurrent.TimeUnit

class GetRequest(url: String, params: Map<String, String>, private val timeout: Int) : HTTPGet
{
    private var genericReq : GenericRequest = GenericRequest("", null)

    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(timeout.toLong(), TimeUnit.SECONDS)
        .readTimeout(timeout.toLong(), TimeUnit.SECONDS)
        .writeTimeout(timeout.toLong(), TimeUnit.SECONDS)
        .build()

    init
    {
        genericReq.url = url
        genericReq.params = params
    }

    override fun getResponse(): Response?
    {
        val httpURLBuilder: HttpUrl.Builder? = genericReq.url.toHttpUrlOrNull()?.newBuilder()

        genericReq.params?.forEach{ (key, value) -> httpURLBuilder?.addQueryParameter(key, value)}

        val request = httpURLBuilder?.let {
            Request.Builder()
                .url(it.build())
                .build()
        }

        return request?.let { client.newCall(it).execute() }
    }

    fun getGenericRequest() : GenericRequest
    {
        return genericReq
    }

}