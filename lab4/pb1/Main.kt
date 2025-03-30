import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.parser.Parser
import org.jsoup.nodes.Element
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.yaml.snakeyaml.Yaml
import com.fasterxml.jackson.module.kotlin.readValue
import khttp.responses.Response

class Crawler(URL: String) {
    private var url : String = URL

    fun getResource() : Response {
        return khttp.get(url)
    }

    fun processContent(contentType : String) {
        when {
            contentType.contains("json") -> println(JsonParser().parse(getResource().text))
            contentType.contains("xml") -> println(XmlParser().parse(getResource().text))
            contentType.contains("yaml") -> println(YamlParser().parse(getResource().text))
            else -> println("Tipul nu este suportat!")
        }
    }
}

interface parser {
    fun parse(text : String) : Map<String, String>
}

class JsonParser : parser {
    override fun parse(text : String) : Map<String, String> {
        val mapper = jacksonObjectMapper()
        val parsedJson : Map<String, Any> = mapper.readValue(text)

        val kotlinMap : MutableMap<String, String> = mutableMapOf<String, String>()
        for((key, value) in parsedJson) {
            kotlinMap[key] = value.toString()
        }

        return kotlinMap
    }
}

class XmlParser : parser {
    override fun parse(text : String) : Map<String, String> {
        val document : Document = Jsoup.parse(text, "", Parser.xmlParser())
        val elements : List<Element> = document.select("*")

        val map : MutableMap<String, String> = mutableMapOf<String, String>()

        for(element in elements){
            map[element.tagName()] = element.text()
        }

        return map
    }
}

class YamlParser : parser {
    override fun parse(text : String) : Map<String, String> {
        val parsedYaml : Map<String, Any> = Yaml().load<Map<String, Any>>(text) ?: emptyMap()
        val kotlinMap : MutableMap<String, String> = mutableMapOf<String, String>()
        for((key, value) in parsedYaml) {
            kotlinMap[key] = value.toString()
        }
        return kotlinMap
    }
}

fun main() {
    val crawler : Crawler = Crawler("https://github.com/PanainteOctavian/PP")

    crawler.getResource().headers["Content-Type"]?.let { crawler.processContent(it) }
}
