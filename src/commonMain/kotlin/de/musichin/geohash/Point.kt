package de.musichin.geohash

class Point private constructor(
    private val coordinates: List<Double>
) : List<Double> by coordinates {
    constructor(coordinates: Iterable<Double>) : this(coordinates.toList())
    constructor(lng: Double, lat: Double) : this(listOf(lng, lat))
    constructor(coordinates: Array<Double>) : this(coordinates.asIterable())
    constructor(coordinates: DoubleArray) : this(coordinates.asIterable())

    init {
        check(size == 2) { "coordinates.size != 2" }
        checkPoint(lng, lat)
    }

    val lng: Double get() = get(0)
    val lat: Double get() = get(1)

    override fun toString(): String = coordinates.toString()

    override fun equals(other: Any?): Boolean {
        if (other !is Point) return false

        return this.coordinates == other.coordinates
    }

    override fun hashCode(): Int = coordinates.hashCode()

    fun hash(precision: Int) = GeoHash.hash(this, precision)

    fun bounds(precision: Int) = GeoHash.bounds(this, precision)
}
