package com.entin.streetmusic.core.model.map

/**
 * Short description for each concert marked on map
 * List of [ShortConcertForMap] is saving in UserSession by PreConcertViewModel
 */

data class ShortConcertForMap(
    val latitude: String = "",
    val longitude: String = "",
    val artistId: String = "",
    val bandName: String = "",
    val address: String = "",
    val create: Long = 0L,
    val styleMusic: String = "",
)