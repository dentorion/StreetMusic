package com.example.network.worldtime.api

import com.example.network.worldtime.model.ServerTimeUtcResponse
import retrofit2.http.GET

interface TimeUtcApiService {

    // GET UTC SERVER API
    @GET("Warsaw/")
    suspend fun getServerUnixTime(): ServerTimeUtcResponse
}