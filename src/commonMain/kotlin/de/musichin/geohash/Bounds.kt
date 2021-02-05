package de.musichin.geohash

class Bounds private constructor(
    private val coordinates: List<Double>
) : List<Double> by coordinates {
    constructor(coordinates: Iterable<Double>) : this(coordinates.toList())
    constructor(sw: Point, ne: Point) : this(sw + ne)
    constructor(coordinates: Array<Double>) : this(coordinates.asIterable())
    constructor(coordinates: DoubleArray) : this(coordinates.asIterable())

    init {
        check(size == 4) { "coordinates.size != 4" }
        checkPoint(get(0), get(1))
        checkPoint(get(2), get(3))
    }

    val southWest: Point get() = Point(slice(0..1))
    val northEast: Point get() = Point(slice(2..3))

    val center: Point
        get() {
            val (minLng, minLat, maxLng, maxLat) = this
            return Point(minLng + (maxLng - minLng) / 2, minLat + (maxLat - minLat))
        }

    fun contains(lng: Double, lat: Double): Boolean =
        contains(Point(lng, lat))

    operator fun contains(point: Point): Boolean =
        point.lng >= get(0) && point.lat >= get(1) && point.lng <= get(2) && point.lat <= get(3)

    override fun toString(): String = coordinates.toString()

    override fun equals(other: Any?): Boolean {
        if (other !is Bounds) return false

        return this.coordinates == other.coordinates
    }

    override fun hashCode(): Int = coordinates.hashCode()
}
