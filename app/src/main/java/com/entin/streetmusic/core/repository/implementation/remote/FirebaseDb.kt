package com.entin.streetmusic.core.repository.implementation.remote

import com.entin.streetmusic.core.repository.interfaces.remote.FirebaseDb
import com.entin.streetmusic.data.firebase.artist.queries.ArtistQueries
import com.entin.streetmusic.data.firebase.avatar.queries.AvatarQueries
import com.entin.streetmusic.data.firebase.concerts.queries.ConcertsByCityStyleQueries
import com.entin.streetmusic.data.firebase.concerts.queries.StartConcertQueries
import com.entin.streetmusic.data.firebase.concerts.queries.StopConcertQueries
import javax.inject.Inject

class FirebaseDb @Inject constructor(
    private val artistQueries: ArtistQueries,
    private val avatarQueries: AvatarQueries,
    private val startConcertQueries: StartConcertQueries,
    private val stopConcertQueries: StopConcertQueries,
    private val sortConcerts: ConcertsByCityStyleQueries,
) : FirebaseDb {

    override fun artistFirebase() = artistQueries

    override fun avatarFirebase() = avatarQueries

    override fun startConcert() = startConcertQueries

    override fun stopConcert() = stopConcertQueries

    override fun sortedConcerts() = sortConcerts
}