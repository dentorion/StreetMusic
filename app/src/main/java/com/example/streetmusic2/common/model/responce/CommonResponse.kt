package com.example.streetmusic2.common.model.responce

@Suppress("UNUSED_PARAMETER")
sealed class CommonResponse<T>(data: T? = null, message: String? = null) {
    data class Error<T>(val message: String) : CommonResponse<T>(message = message)
    data class Success<T>(val data: T) : CommonResponse<T>(data)
    class Load<T> : CommonResponse<T>()
    class Initial<T> : CommonResponse<T>()
}
