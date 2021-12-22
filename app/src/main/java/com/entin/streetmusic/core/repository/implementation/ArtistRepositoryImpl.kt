package com.entin.streetmusic.core.repository.implementation

import com.entin.streetmusic.core.repository.implementation.local.LocalSource
import com.entin.streetmusic.core.repository.implementation.remote.RemoteSource
import com.entin.streetmusic.ui.screens.artist.ArtistRepository
import com.entin.streetmusic.data.database.favourite.FavouriteArtistModel
import javax.inject.Inject

class ArtistRepositoryImpl @Inject constructor(
    private val localSource: LocalSource,
    private val remoteSource: RemoteSource,
) : ArtistRepository {

    // Firebase - get actual artist avatar
    override suspend fun getAvatarUrl(artistId: String, callBack: (String) -> Unit) =
        remoteSource.firebaseDb().avatarFirebase().getAvatarUrl(artistId, callBack)

    // Firebase - get all long (more than 5 minutes) concerts by artist Id
    override suspend fun getAllConcertsByArtist(artistId: String) =
        remoteSource.firebaseDb().artistFirebase().getAllConcertsByArtist(artistId)

    // Is artist in favourite list
    override fun isArtistFavourite(artistId: String): Boolean =
        localSource.roomDb().favourites().isArtistFavourite(artistId = artistId)

    // Add artist to favourite
    override suspend fun addFavouriteArtist(favouriteArtistModel: FavouriteArtistModel) =
        localSource.roomDb().favourites()
            .addFavouriteArtist(favouriteArtistModel = favouriteArtistModel)

    // Delete artist from favourite
    override suspend fun delFavouriteArtist(artistId: String) =
        localSource.roomDb().favourites().delFavouriteArtist(artistId = artistId)
}