package com.example.streetmusic2.util.firebase

import com.example.streetmusic2.common.model.concert.ConcertFirebase
import com.example.streetmusic2.common.model.music.MusicStyle
import com.example.streetmusic2.util.constant.HOUR_ONE_MLS
import com.google.firebase.firestore.CollectionReference
import dagger.hilt.android.scopes.ViewModelScoped
import java.util.*
import javax.inject.Inject

@ViewModelScoped
class ConcertCreateQueries @Inject constructor(
    private val fireBaseDb: CollectionReference
) {

    /**
     * Create concert
     */
    fun createConcert(
        latitude: String,
        longitude: String,
        country: String,
        city: String,
        artistId: String,
        name: String,
        avatar: String,
        styleMusic: MusicStyle,
        address: String,
        description: String,
        callBack: (Boolean) -> Unit
    ) {
        fireBaseDb.add(
            ConcertFirebase(
                latitude = latitude,
                longitude = longitude,
                country = country,
                city = city,
                artistId = artistId,
                name = name,
                avatar = avatar,
                styleMusic = styleMusic.styleName,
                address = address,
                description = description,
                timeStart = Date().time,
                timeStop = Date().time + HOUR_ONE_MLS,
            )
        ).addOnSuccessListener {
            callBack(true)
        }.addOnFailureListener {
            callBack(false)
        }
    }
}