package com.example.streetmusic2.util.time

import com.example.network.worldtime.api.TimeUtcApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServerUnixTime @Inject constructor(
    private val apiService: TimeUtcApiService
) {
    /**
     * Result in SECONDS
     */
    suspend fun get(): Long {
        return apiService.getServerUnixTime().unixtime.toLong()
    }
}