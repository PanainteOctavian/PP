import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.FormBody
import okhttp3.Response
import java.util.concurrent.TimeUnit
import prototype.GenericRequest

class PostRequest(url: String, params: Map<String, String>) {
    private var genericRequest: GenericRequest = GenericRequest("", null)

    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    init {
        genericRequest.url = url
        genericRequest.params = params
    }

    fun postData(): Response {
        val formBody = FormBody.Builder().apply {
            genericRequest.params?.forEach { (key, value) ->
                add(key, value)
            }
        }.build()

        val request = Request.Builder()
            .url(genericRequest.url)
            .post(formBody)
            .build()

        return client.newCall(request).execute()
    }
}
