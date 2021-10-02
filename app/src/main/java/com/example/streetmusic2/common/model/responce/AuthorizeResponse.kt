package com.example.streetmusic2.common.model.responce

import com.google.firebase.auth.FirebaseUser

sealed class AuthorizeResponse {
    object Load: AuthorizeResponse()
    object Initial: AuthorizeResponse()
    data class Error(val value: String): AuthorizeResponse()
    data class Success(val user: FirebaseUser?): AuthorizeResponse()
    data class Navigate(val value: Boolean, val userId: String): AuthorizeResponse()
}