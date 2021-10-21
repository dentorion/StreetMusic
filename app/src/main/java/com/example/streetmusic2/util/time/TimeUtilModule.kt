package com.example.streetmusic2.util.time

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TimeUtilModule {

    @Singleton
    @Provides
    fun provideTimeUtil(@ApplicationContext context: Context): TimeUtil =
        TimeUtil(context = context)
}