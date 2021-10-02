package com.example.streetmusic2.util.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.streetmusic2.common.model.favourite.FavouriteArtist
import com.example.streetmusic2.util.checkfavourite.StreetMusicDAO

@Database(entities = [FavouriteArtist::class], version = 1, exportSchema = false)
abstract class DataBaseRoom : RoomDatabase() {

    abstract fun streetMusicDAO(): StreetMusicDAO

    companion object {
        const val DATABASE_NAME: String = "StreetMusicDatabase"
    }
}