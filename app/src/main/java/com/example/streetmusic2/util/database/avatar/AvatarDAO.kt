package com.example.streetmusic2.util.database.avatar

import androidx.room.*

@Dao
interface AvatarDAO {

    @Query("SELECT * FROM User WHERE artist_id = :artistId LIMIT 1")
    suspend fun getCurrentAvatarUrl(artistId: String): AvatarModel

    @Query("SELECT EXISTS(SELECT * FROM User WHERE artist_id = :artistId LIMIT 1)")
    suspend fun checkForAvatarUrl(artistId: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setNewAvatarUrl(avatar: AvatarModel)

    @Query("UPDATE User SET avatar_url=:url WHERE artist_id = :artistId")
    suspend fun updateAvatarUrl(artistId: String, url: String)

}