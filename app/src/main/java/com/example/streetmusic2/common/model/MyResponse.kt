package com.example.streetmusic2.common.model

@Suppress("UNUSED_PARAMETER")
sealed class MyResponse<T>(data: T? = null, message: String? = null) {
    data class Error<T>(val message: String) : MyResponse<T>(message = message)
    data class Success<T>(val data: T) : MyResponse<T>(data)
    class Load<T> : MyResponse<T>()
    class Initial<T> : MyResponse<T>()
}
