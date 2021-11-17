package com.entin.streetmusic.common.repository.interfaces.local

import com.entin.streetmusic.util.user.UserSession

interface LocalSource {

    fun roomDb(): RoomDb

    fun userSession(): UserSession
}