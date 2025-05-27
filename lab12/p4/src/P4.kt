import java.util.*

fun String.toPascalCase() : String
{
    return this.split(" ").joinToString("") { it.replaceFirstChar { elem -> if (elem.isLowerCase()) elem.titlecase(Locale.getDefault()) else elem.toString() } }
}

class MapFunctor<K, V>
{
    fun map(m: MutableMap<K, V>, transform: (V) -> V) : MutableMap<K, V>
    {
        return m.mapValues { transform(it.value) }.toMutableMap()
    }
}

fun processMap(m : MutableMap<Int, String>)
{
    val functor = MapFunctor<Int, String>()

    val firstMap = functor.map(m) { value -> "Test $value" }

    val secondMap = functor.map(firstMap) { value -> value.toPascalCase() }

    println("Primul map: $firstMap")
    println("Al doilea map: $secondMap")
}

fun main()
{
    val m = mutableMapOf(1 to "Acesta este un sir", 2 to "Acesta este celalalt sir")
    processMap(m)
}
