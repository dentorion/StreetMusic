package com.example.streetmusic2.util.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.streetmusic2.util.database.avatar.AvatarDAO
import com.example.streetmusic2.util.database.avatar.AvatarModel
import com.example.streetmusic2.util.database.checkfavourite.FavouriteArtistModel
import com.example.streetmusic2.util.database.checkfavourite.FavouritesDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider

@Database(
    entities = [FavouriteArtistModel::class, AvatarModel::class],
    version = 1,
    exportSchema = false
)
abstract class DataBaseRoom : RoomDatabase() {

    abstract fun favouritesDao(): FavouritesDAO

    abstract fun avatarDao(): AvatarDAO

    class CallBack @Inject constructor(
        private val database: Provider<DataBaseRoom>,
        @Named("AppCoroutine") private val appScopeCoroutine: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            /**
             * Insert default avatar url to DB with id 1
             * After log in id 1 will be changed up to date userId
             */
            appScopeCoroutine.launch {
//                database.get().avatarDao().setNewAvatarUrl("1")
            }
        }
    }

    companion object {
        const val DATABASE_NAME: String = "StreetMusicDatabase"
    }
}