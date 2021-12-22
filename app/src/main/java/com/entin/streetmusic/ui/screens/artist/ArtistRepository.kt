package com.entin.streetmusic.ui.screens.artist

import com.entin.streetmusic.core.model.domain.ConcertDomain
import com.entin.streetmusic.data.database.favourite.FavouriteArtistModel

interface ArtistRepository {
    suspend fun getAvatarUrl(artistId: String, callBack: (String) -> Unit)
    suspend fun getAllConcertsByArtist(artistId: String): Pair<List<ConcertDomain>, List<ConcertDomain>>
    fun isArtistFavourite(artistId: String): Boolean
    suspend fun addFavouriteArtist(favouriteArtistModel: FavouriteArtistModel)
    suspend fun delFavouriteArtist(artistId: String)
}