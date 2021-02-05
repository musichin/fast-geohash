package de.musichin.geohash

object GeoHash {
    private const val B32 = "0123456789bcdefghjkmnpqrstuvwxyz"

    fun bounds(point: Iterable<Double>, precision: Int): Bounds =
        bounds(Point(point), precision)

    fun bounds(lng: Double, lat: Double, precision: Int): Bounds =
        bounds(Point(lng, lat), precision)

    fun bounds(point: Point, precision: Int): Bounds =
        Bounds(encode(point, precision).second)

    fun hash(point: Iterable<Double>, precision: Int): String =
        hash(Point(point), precision)

    fun hash(lng: Double, lat: Double, precision: Int): String =
        hash(Point(lng, lat), precision)

    fun hash(point: Point, precision: Int): String =
        encode(point, precision).first

    /**
     * Returns geohash and it's bounds.
     */
    fun encode(point: Point, precision: Int): Pair<String, Bounds> {
        check(precision > 0) { "precision <= 0" }
        val (lng, lat) = point

        val hash = StringBuilder(precision)

        val box = doubleArrayOf(-180.0, -90.0, 180.0, 90.0)
        var even = true
        var value: Int
        repeat(precision) {
            value = 0
            (4 downTo 0).forEach { bit ->
                if (even) {
                    val mid = (box[0] + box[2]) / 2
                    if (lng < mid) {
                        box[2] = mid
                    } else {
                        value = value or (1 shl bit)
                        box[0] = mid
                    }
                } else {
                    val mid = (box[1] + box[3]) / 2
                    if (lat < mid) {
                        box[3] = mid
                    } else {
                        value = value or (1 shl bit)
                        box[1] = mid
                    }
                }
                even = !even
            }
            hash.append(B32[value])
        }

        return hash.toString() to Bounds(box)
    }

    fun bounds(hash: String): Bounds {
        val box = doubleArrayOf(-180.0, -90.0, 180.0, 90.0)
        var even = true
        var value: Int
        repeat(hash.length) { character ->
            value = B32.indexOf(hash[character])
            check(value >= 0) { "$character not valid" }
            (4 downTo 0).forEach { bit ->
                if (even) {
                    val mid = (box[0] + box[2]) / 2
                    if ((value and (1 shl bit)) == 0) {
                        box[2] = mid
                    } else {
                        box[0] = mid
                    }
                } else {
                    val mid = (box[1] + box[3]) / 2
                    if ((value and (1 shl bit)) == 0) {
                        box[3] = mid
                    } else {
                        box[1] = mid
                    }
                }
                even = !even
            }
        }

        return Bounds(box)
    }

    fun point(hash: String): Point = bounds(hash).center
}
