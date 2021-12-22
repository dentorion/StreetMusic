package com.entin.streetmusic.data.time

import com.entin.network.worldtime.api.TimeUtcApiService
import javax.inject.Inject

class ServerUnixTime @Inject constructor(
    private val apiService: TimeUtcApiService
) {
    /**
     * Result in SECONDS
     */
    suspend fun get(): Long =
        apiService.getServerUnixTime().result.toLong()
}