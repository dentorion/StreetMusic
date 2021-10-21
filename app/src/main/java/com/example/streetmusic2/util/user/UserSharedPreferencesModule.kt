package com.example.streetmusic2.util.user

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserSharedPreferencesModule {

    @Singleton
    @Provides
    fun provideUserSharedPreferences(@ApplicationContext context: Context): UserSharedPreferences =
        UserSharedPreferences(context = context)
}