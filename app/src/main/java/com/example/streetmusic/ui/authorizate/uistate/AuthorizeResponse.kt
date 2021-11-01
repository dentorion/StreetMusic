package com.example.streetmusic.ui.authorizate.uistate

import com.google.firebase.auth.FirebaseUser

sealed class AuthorizeResponse {
    object Load: AuthorizeResponse()
    object Initial: AuthorizeResponse()
    data class Error(val value: String): AuthorizeResponse()
    data class Success(val user: FirebaseUser?): AuthorizeResponse()
    data class Navigate(val artistId: String, val documentId: String?): AuthorizeResponse()
}