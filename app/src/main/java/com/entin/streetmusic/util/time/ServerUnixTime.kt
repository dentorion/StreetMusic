package com.entin.streetmusic.util.time

import com.entin.network.worldtime.api.TimeUtcApiService
import javax.inject.Inject
import javax.inject.Singleton

class ServerUnixTime @Inject constructor(
    private val apiService: TimeUtcApiService
) {
    /**
     * Result in SECONDS
     */
    suspend fun get(): Long =
        apiService.getServerUnixTime().result.toLong()
}