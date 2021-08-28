package com.example.streetmusic2.common.model

import java.util.*

data class Concert(
    val latitude: String = "",
    val longitude: String = "",
    val country: String = "",
    val city: String = "",

    val id: Int = 0,
    val name: String = "",
    val avatar: String = "",
    val styleMusic: String = "",
    val timeStart: Date? = null,
    val timeStop: Date? = null,
)
