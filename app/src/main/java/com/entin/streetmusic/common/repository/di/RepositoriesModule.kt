package com.entin.streetmusic.common.repository.di

import com.entin.streetmusic.common.repository.implementation.*
import com.entin.streetmusic.ui.artist.ArtistRepository
import com.entin.streetmusic.ui.authorizate.AuthorizeRepository
import com.entin.streetmusic.ui.cityconcerts.CityConcertsRepository
import com.entin.streetmusic.ui.concert.ConcertRepository
import com.entin.streetmusic.ui.permissions.PermissionsRepository
import com.entin.streetmusic.ui.preconcert.PreConcertRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun bindArtistRepository(
        artistRepositoryImpl: ArtistRepositoryImpl
    ): ArtistRepository

    @Binds
    abstract fun bindCityConcertsRepository(
        cityConcertsRepositoryImpl: CityConcertsRepositoryImpl
    ): CityConcertsRepository

    @Binds
    abstract fun bindConcertRepository(
        concertsRepositoryImpl: ConcertRepositoryImpl
    ): ConcertRepository

    @Binds
    abstract fun bindPreConcertRepository(
        preConcertRepositoryImpl: PreConcertRepositoryImpl
    ): PreConcertRepository

    @Binds
    abstract fun bindPermissionsRepository(
        permissionsRepositoryImpl: PermissionsRepositoryImpl
    ): PermissionsRepository

    @Binds
    abstract fun bindAuthorizeRepository(
        authorizeRepositoryImpl: AuthorizeRepositoryImpl
    ): AuthorizeRepository
}