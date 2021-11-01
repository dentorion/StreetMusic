package com.example.streetmusic.util.user

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserSessionModule {

    @Singleton
    @Provides
    fun provideUserSession(@ApplicationContext context: Context): UserSession =
        UserSession(context = context)
}