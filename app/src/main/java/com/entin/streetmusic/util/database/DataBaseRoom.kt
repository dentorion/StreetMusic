package com.entin.streetmusic.util.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.entin.streetmusic.util.database.favourite.FavouriteArtistModel
import com.entin.streetmusic.util.database.favourite.FavouritesDAO

@Database(
    entities = [FavouriteArtistModel::class],
    version = 1,
    exportSchema = false
)
abstract class DataBaseRoom : RoomDatabase() {

    abstract fun favouritesDao(): FavouritesDAO

    companion object {
        const val DATABASE_NAME: String = "StreetMusicDatabase"
    }
}