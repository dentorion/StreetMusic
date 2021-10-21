package com.example.streetmusic2.util.database.avatar

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.streetmusic2.util.constant.DEFAULT_ID_ARTIST
import com.example.streetmusic2.util.constant.DEFAULT_URL_AVATAR

@Entity(tableName = "User")
data class AvatarModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "artist_id") val artistId: String = DEFAULT_ID_ARTIST,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String = DEFAULT_URL_AVATAR
)