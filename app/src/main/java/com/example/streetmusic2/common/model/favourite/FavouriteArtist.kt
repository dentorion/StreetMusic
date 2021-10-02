package com.example.streetmusic2.common.model.favourite

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavouriteArtist(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idArtist: String,
    val isFavourite: Boolean = false
)