package com.entin.streetmusic.util.database.checkfavourite

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ArtistsFavouriteRoom @Inject constructor(
    private val favouritesDAO: FavouritesDAO
) {
    /**
     * Check if artist in favourite list by his id
     */
    fun checkFavouriteById(artistId: String): Boolean =
        favouritesDAO.checkArtistFavourite(artistId = artistId)

    /**
     * Add artist to favourite
     */
    suspend fun addFavourite(favouriteArtistModel: FavouriteArtistModel) =
        favouritesDAO.addFavourite(favouriteArtistModel = favouriteArtistModel)

    /**
     * Delete artist from favourite
     */
    suspend fun delFavourite(artistId: String) =
        favouritesDAO.deleteFavourite(artistId = artistId)
}