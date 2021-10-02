package com.example.streetmusic2.ui.authorizate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.streetmusic2.common.model.responce.AuthorizeResponse
import com.example.streetmusic2.util.firebase.ConcertsByArtistQueries
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AuthorizeViewModel @Inject constructor(
    private val concertsByArtist: ConcertsByArtistQueries
) : ViewModel() {

    var authorizeState: AuthorizeResponse by mutableStateOf(AuthorizeResponse.Initial)
        private set

    /**
     * Google authorization returns Firebase user
     */
    fun signWithCredential(credential: AuthCredential) = viewModelScope.launch {
        try {
            authorizeState = AuthorizeResponse.Load
            val user = Firebase.auth.signInWithCredential(credential).await().user
            authorizeState = AuthorizeResponse.Success(user = user)
        } catch (e: Exception) {
            authorizeState = AuthorizeResponse.Error(e.localizedMessage ?: "error auth mvvm")
        }
    }

    /**
     * Check Artist for running concert
     * False -> actual concert is present in Firebase
     * True -> no actual concert in Firebase
     */
    fun checkOnlineConcertByUser(id: String) = viewModelScope.launch {
        authorizeState = AuthorizeResponse.Load
        val result = concertsByArtist.getConcertsAllByArtist(artistId = id)
        authorizeState = AuthorizeResponse.Navigate(result.first.isEmpty(), id)
    }
}

