package com.example.streetmusic2.util.firebase.di

import com.example.streetmusic2.util.constant.CONCERTS_COLLECTION
import com.example.streetmusic2.util.constant.USERS_COLLECTION
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

    @Named("concerts")
    @Provides
    @Singleton
    fun provideFirebaseConcerts(): CollectionReference =
        Firebase.firestore
            .collection(CONCERTS_COLLECTION)

    @Named("users")
    @Provides
    @Singleton
    fun provideFirebaseUsers(): CollectionReference =
        Firebase.firestore
            .collection(USERS_COLLECTION)
}
