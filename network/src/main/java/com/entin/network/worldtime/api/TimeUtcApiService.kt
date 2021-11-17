package com.entin.network.worldtime.api

import com.entin.network.worldtime.model.ServerTimeUtcResponse
import retrofit2.http.GET

interface TimeUtcApiService {

    /**
     * GET UTC SERVER API
     */
    @GET("serverTimeStamp")
    suspend fun getServerUnixTime(): ServerTimeUtcResponse
}