package com.entin.streetmusic.common.repository.interfaces.remote

import com.entin.streetmusic.util.firebase.artist.queries.ArtistQueries
import com.entin.streetmusic.util.firebase.avatar.queries.AvatarQueries
import com.entin.streetmusic.util.firebase.concerts.queries.ConcertsByCityStyleQueries
import com.entin.streetmusic.util.firebase.concerts.queries.StartConcertQueries
import com.entin.streetmusic.util.firebase.concerts.queries.StopConcertQueries

interface FirebaseDb {

    fun artistFirebase(): ArtistQueries

    fun avatarFirebase(): AvatarQueries

    fun startConcert(): StartConcertQueries

    fun stopConcert(): StopConcertQueries

    fun sortedConcerts(): ConcertsByCityStyleQueries
}