package com.entin.network.worldtime.model

import com.google.gson.annotations.Expose

/**
 * Firebase functions returns json with fields:
 *  - result: ServerTimeStamp
 */
data class ServerTimeUtcResponse(
    @Expose
    val result: String,
)