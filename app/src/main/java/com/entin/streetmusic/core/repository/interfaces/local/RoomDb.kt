package com.entin.streetmusic.core.repository.interfaces.local

import com.entin.streetmusic.data.database.favourite.ArtistsFavouriteRoom

interface RoomDb {

    fun favourites(): ArtistsFavouriteRoom
}