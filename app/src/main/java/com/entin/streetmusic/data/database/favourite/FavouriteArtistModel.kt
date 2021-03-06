package com.entin.streetmusic.data.database.favourite

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * For saving favourite in Room DB
 */
@Entity
data class FavouriteArtistModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idArtist: String,
    val isFavourite: Boolean = false
)