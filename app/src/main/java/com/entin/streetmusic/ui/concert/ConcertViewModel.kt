package com.entin.streetmusic.ui.concert

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.entin.streetmusic.common.model.vmstate.CommonResponse
import com.entin.streetmusic.common.constants.DEFAULT_URL_AVATAR
import com.entin.streetmusic.util.database.avatar.AvatarRoom
import com.entin.streetmusic.util.firebase.StopConcertQueries
import com.entin.streetmusic.util.user.UserSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ConcertViewModel @Inject constructor(
    private val stopConcertQueries: StopConcertQueries,
    private val avatarRoom: AvatarRoom,
    userPref: UserSession,
) : ViewModel() {

    /**
     * State
     */
    var stateConcert: CommonResponse<Boolean> by mutableStateOf(CommonResponse.Initial())
        private set

    var avatarUrl: String = DEFAULT_URL_AVATAR

    init {
        setAvatar(userPref.getId())
    }

    fun stopConcert(documentId: String) = viewModelScope.launch {
        stateConcert = CommonResponse.Load()
        stopConcertQueries.stopConcert(documentId) { result ->
            stateConcert = if (result) {
                CommonResponse.Success(true)
            } else {
                CommonResponse.Error("Can't stop concert")
            }
        }
    }

    private fun setAvatar(artistId: String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            avatarUrl = avatarRoom.getCurrentAvatarUrl(artistId).avatarUrl
        }
    }
}