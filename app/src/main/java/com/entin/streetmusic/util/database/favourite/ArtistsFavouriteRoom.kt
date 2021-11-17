package com.entin.streetmusic.util.database.favourite

import javax.inject.Inject

class ArtistsFavouriteRoom @Inject constructor(
    private val favouritesDAO: FavouritesDAO
) {
    /**
     * Check if artist in favourite list by his id
     */
    fun isArtistFavourite(artistId: String): Boolean =
        favouritesDAO.checkArtistFavourite(artistId = artistId)

    /**
     * Add artist to favourite
     */
    suspend fun addFavouriteArtist(favouriteArtistModel: FavouriteArtistModel) =
        favouritesDAO.addFavourite(favouriteArtistModel = favouriteArtistModel)

    /**
     * Delete artist from favourite
     */
    suspend fun delFavouriteArtist(artistId: String) =
        favouritesDAO.deleteFavourite(artistId = artistId)
}