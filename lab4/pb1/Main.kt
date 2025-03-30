import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.parser.Parser
import org.jsoup.nodes.Element
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.yaml.snakeyaml.Yaml
import com.fasterxml.jackson.module.kotlin.readValue
import khttp.responses.Response

// clasa crawler primeste un url si are metode pentru a obtine si procesa continutul
class Crawler(URL: String) {
    private var url : String = URL

    // functie care face o cerere http get la url si returneaza raspunsul
    fun getResource() : Response {
        return khttp.get(url)
    }

    // functie care verifica tipul continutului si apeleaza parserul potrivit
    fun processContent(contentType : String) {
        when {
            contentType.contains("json") -> println(JsonParser().parse(getResource().text))
            contentType.contains("xml") -> println(XmlParser().parse(getResource().text))
            contentType.contains("yaml") -> println(YamlParser().parse(getResource().text))
            else -> println("Tipul nu este suportat!")
        }
    }
}

// interfata care defineste metoda parse pentru toate tipurile de parsere
interface parser {
    fun parse(text : String) : Map<String, String>
}

// parser pentru json care foloseste jackson pentru a converti textul in map
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

// parser pentru xml care foloseste jsoup pentru a extrage tag-urile si textul
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

// parser pentru yaml care foloseste snakeyaml pentru a incarca continutul
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

// functia main care creeaza un crawler si incearca sa parseze continutul
fun main() {
    val crawler : Crawler = Crawler("https://github.com/PanainteOctavian/PP")

    crawler.getResource().headers["Content-Type"]?.let { crawler.processContent(it) }
}  