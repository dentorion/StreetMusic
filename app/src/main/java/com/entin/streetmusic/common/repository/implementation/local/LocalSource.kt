package com.entin.streetmusic.common.repository.implementation.local

import com.entin.streetmusic.common.repository.interfaces.local.LocalSource
import com.entin.streetmusic.util.user.UserSession
import javax.inject.Inject

class LocalSource @Inject constructor(
    private val roomDb: RoomDb,
    private val userSession: UserSession,
) : LocalSource {

    override fun roomDb() = roomDb

    override fun userSession() = userSession
}