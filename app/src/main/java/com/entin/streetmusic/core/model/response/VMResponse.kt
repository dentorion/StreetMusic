package com.entin.streetmusic.core.model.response

/**
 * ViewModel Response to Composable functions
 */

sealed class SMResponse<T> {

    sealed class ResultResponse<T>  : SMResponse<T>()

    data class ErrorResponse<T>(val SMError: SMError) : ResultResponse<T>()

    data class SuccessResponse<T>(val data: T) : ResultResponse<T>()

    class LoadResponse<T> : SMResponse<T>()

    class InitialResponse<T> : SMResponse<T>()
}

/**
 * Inside error class of SMResponse.ErrorResponse()
 */

sealed class SMError {

    // PreConcert ViewModel
    object ImgUrlNull: SMError()
    object FailedStartConcert: SMError()

    // Permissions ViewModel
    object LocationEmpty: SMError()

    // Concert ViewModel
    object FailedStopConcert: SMError()

    // Authorize ViewModel
    object UserNull: SMError()

    // Exception message
    data class ExceptionError(val message: String): SMError()
}