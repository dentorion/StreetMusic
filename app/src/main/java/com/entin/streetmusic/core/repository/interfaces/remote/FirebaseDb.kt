package com.entin.streetmusic.core.repository.interfaces.remote

import com.entin.streetmusic.data.firebase.artist.queries.ArtistQueries
import com.entin.streetmusic.data.firebase.avatar.queries.AvatarQueries
import com.entin.streetmusic.data.firebase.concerts.queries.ConcertsByCityStyleQueries
import com.entin.streetmusic.data.firebase.concerts.queries.StartConcertQueries
import com.entin.streetmusic.data.firebase.concerts.queries.StopConcertQueries

interface FirebaseDb {

    fun artistFirebase(): ArtistQueries

    fun avatarFirebase(): AvatarQueries

    fun startConcert(): StartConcertQueries

    fun stopConcert(): StopConcertQueries

    fun sortedConcerts(): ConcertsByCityStyleQueries
}