package com.entin.streetmusic.common.repository.implementation.remote

import android.content.Context
import com.entin.network.worldtime.api.TimeUtcApiService
import com.entin.streetmusic.common.repository.interfaces.remote.ApiTimeServer
import com.entin.streetmusic.util.time.ServerUnixTime
import com.entin.streetmusic.util.time.TimeUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ApiTimeServer @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: TimeUtcApiService
) : ApiTimeServer {

    override fun serverUnixTime() = ServerUnixTime(apiService)

    override fun timeUtil() = TimeUtil(context)
}