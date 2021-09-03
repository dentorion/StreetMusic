package com.example.streetmusic2.common.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavouriteArtist(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idArtist: Int,
    val isFavourite: Boolean = false
)