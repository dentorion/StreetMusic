package com.entin.streetmusic.core.repository.implementation.local

import com.entin.streetmusic.core.repository.interfaces.local.RoomDb
import com.entin.streetmusic.data.database.favourite.ArtistsFavouriteRoom
import javax.inject.Inject

class RoomDb @Inject constructor(
    private val artistFavouriteRoom: ArtistsFavouriteRoom,
) : RoomDb {

    override fun favourites() = artistFavouriteRoom
}