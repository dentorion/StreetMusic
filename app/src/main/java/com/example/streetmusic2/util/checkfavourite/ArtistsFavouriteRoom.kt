package com.example.streetmusic2.util.checkfavourite

import com.example.streetmusic2.common.model.FavouriteArtist
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ArtistsFavouriteRoom @Inject constructor(
    private val streetMusicDAO: StreetMusicDAO
) {
    /**
     * Check if artist in favourite list by his id
     */
    fun checkFavouriteById(artistId: Int): Boolean =
        streetMusicDAO.checkArtistFavourite(artistId = artistId)

    /**
     * Add artist to favourite
     */
    suspend fun addFavourite(favouriteArtist: FavouriteArtist) =
        streetMusicDAO.addFavourite(favouriteArtist = favouriteArtist)

    /**
     * Delete artist from favourite
     */
    suspend fun delFavourite(artistId: Int) =
        streetMusicDAO.deleteFavourite(artistId = artistId)
}