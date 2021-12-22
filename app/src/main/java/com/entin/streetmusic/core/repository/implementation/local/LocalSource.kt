package com.entin.streetmusic.core.repository.implementation.local

import com.entin.streetmusic.core.repository.interfaces.local.LocalSource
import com.entin.streetmusic.data.user.UserSession
import javax.inject.Inject

class LocalSource @Inject constructor(
    private val roomDb: RoomDb,
    private val userSession: UserSession,
) : LocalSource {

    override fun roomDb() = roomDb

    override fun userSession() = userSession
}