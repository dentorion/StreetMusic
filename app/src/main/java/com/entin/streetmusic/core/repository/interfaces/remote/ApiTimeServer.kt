package com.entin.streetmusic.core.repository.interfaces.remote

import com.entin.streetmusic.data.time.ServerUnixTime
import com.entin.streetmusic.data.time.TimeUtil

interface ApiTimeServer {

    fun serverUnixTime(): ServerUnixTime

    fun timeUtil(): TimeUtil
}