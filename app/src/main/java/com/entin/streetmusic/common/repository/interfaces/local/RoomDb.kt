package com.entin.streetmusic.common.repository.interfaces.local

import com.entin.streetmusic.util.database.favourite.ArtistsFavouriteRoom

interface RoomDb {

    fun favourites(): ArtistsFavouriteRoom
}