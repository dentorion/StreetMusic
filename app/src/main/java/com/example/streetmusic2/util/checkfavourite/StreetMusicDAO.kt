package com.example.streetmusic2.util.checkfavourite

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.streetmusic2.common.model.favourite.FavouriteArtist

@Dao
interface StreetMusicDAO {
    @Query("SELECT * FROM FavouriteArtist WHERE idArtist = :artistId")
    fun checkArtistFavourite(artistId: String): Boolean

    @Insert
    suspend fun addFavourite(favouriteArtist: FavouriteArtist)

    @Query("DELETE FROM FavouriteArtist WHERE idArtist = :artistId")
    suspend fun deleteFavourite(artistId: String)
}