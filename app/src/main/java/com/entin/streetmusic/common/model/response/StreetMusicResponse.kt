package com.entin.streetmusic.common.model.response

@Suppress("UNUSED_PARAMETER")
sealed class StreetMusicResponse<T>(data: T? = null, message: String? = null) {

    data class Error<T>(val message: String) : StreetMusicResponse<T>(message = message)

    data class Success<T>(val data: T) : StreetMusicResponse<T>(data)

    class Load<T> : StreetMusicResponse<T>()

    class Initial<T> : StreetMusicResponse<T>()
}