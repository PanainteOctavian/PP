fun processList(input: List<Int>) : Int
{
    return input.filter{ it >= 5 }
        .chunked(2)
        .mapNotNull{ if(it.size == 2) it[0] * it[1] else null }
        .sum()
}

fun main()
{
    val list = listOf(1, 21, 75, 39, 7, 2, 35, 3, 31, 7, 8)
    println("Lista procesata : ${processList(list)}")
}