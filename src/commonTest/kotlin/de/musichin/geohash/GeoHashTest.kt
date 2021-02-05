package de.musichin.geohash

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GeoHashTest {
    @Test
    fun encode() {
        val hash = GeoHash.hash(9.0, 48.4, 7)
        assertEquals("u0w7kfc", hash)
    }

    @Test
    fun decode() {
        val bounds = GeoHash.bounds("u0w7kfc")
        assertTrue(bounds.contains(9.0, 48.4), bounds.toString())
        print(bounds)
        print(bounds.center)
    }
}
