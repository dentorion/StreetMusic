package com.example.streetmusic2.util.database.database

import android.content.Context
import androidx.room.Room
import com.example.streetmusic2.util.database.avatar.AvatarDAO
import com.example.streetmusic2.util.database.checkfavourite.FavouritesDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun provideDb(
        @ApplicationContext context: Context,
        callback: DataBaseRoom.CallBack
    ): DataBaseRoom =
        Room.databaseBuilder(
            context,
            DataBaseRoom::class.java,
            DataBaseRoom.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()

    @Singleton
    @Provides
    fun provideFavouritesDAO(db: DataBaseRoom): FavouritesDAO = db.favouritesDao()

    @Singleton
    @Provides
    fun provideAvatarDAO(db: DataBaseRoom): AvatarDAO = db.avatarDao()

    @Named("AppCoroutine")
    @Singleton
    @Provides
    fun provideAppScope() =
        CoroutineScope(SupervisorJob())
}