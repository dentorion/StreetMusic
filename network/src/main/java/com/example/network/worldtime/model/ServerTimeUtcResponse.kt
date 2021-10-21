package com.example.network.worldtime.model

import com.google.gson.annotations.Expose

data class ServerTimeUtcResponse(

    val abbreviation: String,

    val client_ip: String,
    @Expose
    val datetime: String,
    @Expose
    val day_of_week: Int,
    @Expose
    val day_of_year: Int,

    val dst: Boolean,

    val dst_from: String,

    val dst_offset: Int,

    val dst_until: String,

    val raw_offset: Int,

    val timezone: String,
    @Expose
    val unixtime: Int,
    @Expose
    val utc_datetime: String,

    val utc_offset: String,

    val week_number: Int
)