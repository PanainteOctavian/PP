import kotlin.math.sqrt
import kotlin.math.pow
import kotlin.math.atan2

data class Point(val x: Double, val y: Double)

fun calculateCentroid(points: List<Point>): Point
{
    val x = points.sumOf { it.x } / points.size
    val y = points.sumOf { it.y } / points.size
    return Point(x, y)
}

fun sortPoints(points: List<Point>): List<Point>
{
    val centroid = calculateCentroid(points)
    return points.sortedBy { atan2(it.y - centroid.y, it.x - centroid.x) }
}

fun calculatePerimeter(points: List<Point>): Double
{
    val distances = points.zipWithNext { a, b ->
        sqrt((b.x - a.x).pow(2) + (b.y - a.y).pow(2))
    }
    val firstLastDistance = sqrt((points.last().x - points.first().x).pow(2) + (points.last().y - points.first().y).pow(2))
    return distances.sum() + firstLastDistance
}

fun main()
{
    val points = listOf(Point(0.0, 0.0), Point(0.0, 1.0), Point(1.0, 0.0), Point(1.0, 1.0))
    println(calculatePerimeter(sortPoints(points)))
}
