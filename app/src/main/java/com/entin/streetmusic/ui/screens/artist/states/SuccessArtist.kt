package com.entin.streetmusic.ui.screens.artist.states

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.annotation.ExperimentalCoilApi
import com.entin.streetmusic.core.model.domain.ConcertDomain
import com.entin.streetmusic.core.model.response.SMResponse
import com.entin.streetmusic.core.theme.StreetMusicTheme
import com.entin.streetmusic.ui.screens.artist.ArtistResponse
import com.entin.streetmusic.ui.screens.artist.components.ConcertsList
import com.entin.streetmusic.ui.screens.artist.components.Header

@ExperimentalCoilApi
@Composable
fun SuccessArtist(
    state: SMResponse.SuccessResponse<ArtistResponse>,
    onLikeClick: () -> Unit
) {
    val artistActiveConcertList = state.data.lists.first
    val artistExpiredConcertList = state.data.lists.second
    val artistAvatarUrl = state.data.avatar
    val isFavouriteArtist = state.data.isFavouriteArtist

    /**
     * Decide where band name, city, country should be taken from:
     * Active concert (only one) or last Finished concert
     */
    val actualSourceOfArtistData = if (artistActiveConcertList.isNotEmpty()) {
        artistActiveConcertList
    } else {
        artistExpiredConcertList
    }

    SuccessArtistContent(
        artistActiveConcertDomain = artistActiveConcertList,
        artistExpiredConcertDomain = artistExpiredConcertList,
        isFavouriteArtist = isFavouriteArtist,
        artistOnline = artistActiveConcertList.isNotEmpty(),
        artistName = actualSourceOfArtistData[0].bandName,
        artistAvatar = artistAvatarUrl,
        artistCity = actualSourceOfArtistData[0].city,
        artistCountry = actualSourceOfArtistData[0].country,
        onLikeClick = onLikeClick,
    )
}

@ExperimentalCoilApi
@Composable
private fun SuccessArtistContent(
    artistActiveConcertDomain: List<ConcertDomain>,
    artistExpiredConcertDomain: List<ConcertDomain>,
    isFavouriteArtist: Boolean,
    artistOnline: Boolean,
    artistName: String,
    artistAvatar: String,
    artistCity: String,
    artistCountry: String,
    onLikeClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        /**
         * HEADER
         */
        Header(
            artistAvatar = artistAvatar,
            artistOnline = artistOnline,
            artistName = artistName,
            artistCity = artistCity,
            artistCountry = artistCountry,
            isFavouriteArtist = isFavouriteArtist,
            onLikeClick = onLikeClick
        )

        /**
         * Small pink line between Recycler and buttons
         */
        Divider(
            color = StreetMusicTheme.colors.divider,
            thickness = StreetMusicTheme.dimensions.dividerThickness
        )

        /**
         * List of concerts: actual and finished
         */
        ConcertsList(artistActiveConcertDomain, artistExpiredConcertDomain)
    }
}