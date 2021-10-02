package com.example.streetmusic2.util.firebase

import com.google.firebase.firestore.CollectionReference
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class StopConcertQueries @Inject constructor(
    private val fireBaseDb: CollectionReference
) {

    fun stopConcert(uId: String, callback: (Boolean) -> Unit) {

    }
}