package com.entin.streetmusic.core.model.response

/**
 * Repository classes Response to ViewModel functions
 */

sealed class RepoResponse<T> {

    data class ErrorResponse<T>(val message: T) : RepoResponse<T>()

    data class SuccessResponse<T>(val data: T) : RepoResponse<T>()
}