package com.entin.streetmusic.data.database.di

import android.content.Context
import androidx.room.Room
import com.entin.streetmusic.data.database.DataBaseRoom
import com.entin.streetmusic.data.database.favourite.FavouritesDAO
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
    ): DataBaseRoom =
        Room.databaseBuilder(
            context,
            DataBaseRoom::class.java,
            DataBaseRoom.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideFavouritesDAO(db: DataBaseRoom): FavouritesDAO = db.favouritesDao()

    @Named("AppCoroutine")
    @Singleton
    @Provides
    fun provideAppScope() =
        CoroutineScope(SupervisorJob())
}