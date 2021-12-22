package com.entin.streetmusic.core.repository.interfaces.local

import com.entin.streetmusic.data.user.UserSession

interface LocalSource {

    fun roomDb(): RoomDb

    fun userSession(): UserSession
}