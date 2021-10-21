package com.example.streetmusic2.util.database.checkfavourite

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