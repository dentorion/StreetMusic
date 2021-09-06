package com.example.streetmusic2.util.checkfavourite

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.streetmusic2.common.model.FavouriteArtist

@Dao
interface StreetMusicDAO {
    @Query("SELECT * FROM FavouriteArtist WHERE idArtist = :artistId")
    fun checkArtistFavourite(artistId: Int): Boolean

    @Insert
    suspend fun addFavourite(favouriteArtist: FavouriteArtist)

    @Query("DELETE FROM FavouriteArtist WHERE idArtist = :artistId")
    suspend fun deleteFavourite(artistId: Int)
}