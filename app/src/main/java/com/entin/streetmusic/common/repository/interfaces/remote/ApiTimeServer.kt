package com.entin.streetmusic.common.repository.interfaces.remote

import com.entin.streetmusic.util.time.ServerUnixTime
import com.entin.streetmusic.util.time.TimeUtil

interface ApiTimeServer {

    fun serverUnixTime(): ServerUnixTime

    fun timeUtil(): TimeUtil
}