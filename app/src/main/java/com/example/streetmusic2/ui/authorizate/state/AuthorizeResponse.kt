package com.example.streetmusic2.ui.authorizate.state

import com.google.firebase.auth.FirebaseUser

sealed class AuthorizeResponse {
    object Load: AuthorizeResponse()
    object Initial: AuthorizeResponse()
    data class Error(val value: String): AuthorizeResponse()
    data class Success(val user: FirebaseUser?): AuthorizeResponse()
    data class Navigate(val artistId: String, val documentId: String?): AuthorizeResponse()
}