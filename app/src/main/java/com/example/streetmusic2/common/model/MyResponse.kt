package com.example.streetmusic2.common.model

sealed class MyResponse<T>(data: T? = null, message: String? = null) {
    data class Error<T>(val message: String, val data: T? = null) : MyResponse<T>(data, message)
    data class Success<T>(val data: T) : MyResponse<T>(data)
    class Load<T>(data: T? = null): MyResponse<T>(data)
}
