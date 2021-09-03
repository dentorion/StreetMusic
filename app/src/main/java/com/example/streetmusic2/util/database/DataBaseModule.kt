package com.example.streetmusic2.util.database

import android.content.Context
import androidx.room.Room
import com.example.streetmusic2.util.checkfavourite.StreetMusicDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): DataBaseRoom =
        Room.databaseBuilder(
            context,
            DataBaseRoom::class.java,
            DataBaseRoom.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideDAO(db: DataBaseRoom): StreetMusicDAO = db.streetMusicDAO()
}