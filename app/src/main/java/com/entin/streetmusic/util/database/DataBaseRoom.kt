package com.entin.streetmusic.util.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.entin.streetmusic.util.database.avatar.AvatarDAO
import com.entin.streetmusic.util.database.avatar.AvatarModel
import com.entin.streetmusic.util.database.checkfavourite.FavouriteArtistModel
import com.entin.streetmusic.util.database.checkfavourite.FavouritesDAO

@Database(
    entities = [FavouriteArtistModel::class, AvatarModel::class],
    version = 1,
    exportSchema = false
)
abstract class DataBaseRoom : RoomDatabase() {

    abstract fun favouritesDao(): FavouritesDAO

    abstract fun avatarDao(): AvatarDAO

    companion object {
        const val DATABASE_NAME: String = "StreetMusicDatabase"
    }
}