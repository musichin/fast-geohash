package de.musichin.geohash

internal fun checkPoint(lng: Double, lat: Double) {
    check(lng <= 180) { "lng > 180" }
    check(lng >= -180) { "lng < 180" }
    check(lat <= 90) { "lat > 90" }
    check(lat >= -90) { "lat < 90" }
}
