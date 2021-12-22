package com.entin.streetmusic.data.database.favourite

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavouritesDAO {

    @Query("SELECT * FROM FavouriteArtistModel WHERE idArtist = :artistId")
    fun checkArtistFavourite(artistId: String): Boolean

    @Insert
    suspend fun addFavourite(favouriteArtistModel: FavouriteArtistModel)

    @Query("DELETE FROM FavouriteArtistModel WHERE idArtist = :artistId")
    suspend fun deleteFavourite(artistId: String)
}