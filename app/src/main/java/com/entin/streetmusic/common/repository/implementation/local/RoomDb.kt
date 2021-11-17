package com.entin.streetmusic.common.repository.implementation.local

import com.entin.streetmusic.common.repository.interfaces.local.RoomDb
import com.entin.streetmusic.util.database.favourite.ArtistsFavouriteRoom
import javax.inject.Inject

class RoomDb @Inject constructor(
    private val artistFavouriteRoom: ArtistsFavouriteRoom,
) : RoomDb {

    override fun favourites() = artistFavouriteRoom
}