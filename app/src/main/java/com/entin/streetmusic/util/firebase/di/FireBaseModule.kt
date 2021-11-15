package com.entin.streetmusic.util.firebase.di

import com.entin.streetmusic.util.firebase.constant.AVATARS_NAME_HILT
import com.entin.streetmusic.util.firebase.constant.CONCERTS_NAME_HILT
import com.entin.streetmusic.util.firebase.constant.ERROR_NAME_HILT
import com.entin.streetmusic.util.firebase.constant.USERS_NAME_HILT
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FireBaseModule {

    /**
     * Concerts firebase collection
     */
    @Named(CONCERTS_NAME_HILT)
    @Provides
    @Singleton
    fun provideFirebaseConcerts(): CollectionReference =
        Firebase.firestore.collection(CONCERTS_NAME_HILT)

    /**
     * Users collection
     */
    @Named(USERS_NAME_HILT)
    @Provides
    @Singleton
    fun provideFirebaseUsers(): CollectionReference =
        Firebase.firestore.collection(USERS_NAME_HILT)

    /**
     * Avatars collection
     */
    @Named(AVATARS_NAME_HILT)
    @Provides
    @Singleton
    fun provideFirebaseAvatars(): CollectionReference =
        Firebase.firestore.collection(AVATARS_NAME_HILT)

    /**
     * Errors collection
     */
    @Named(ERROR_NAME_HILT)
    @Provides
    @Singleton
    fun provideFirebaseErrors(): CollectionReference =
        Firebase.firestore.collection(ERROR_NAME_HILT)
}
