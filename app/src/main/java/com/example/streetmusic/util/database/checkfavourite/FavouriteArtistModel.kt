package com.example.streetmusic.util.database.checkfavourite

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavouriteArtistModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idArtist: String,
    val isFavourite: Boolean = false
)