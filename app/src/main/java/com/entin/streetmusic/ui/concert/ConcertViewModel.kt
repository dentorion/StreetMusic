package com.entin.streetmusic.ui.concert

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.entin.streetmusic.common.model.vmstate.CommonResponse
import com.entin.streetmusic.util.firebase.concerts.queries.StopConcertQueries
import com.entin.streetmusic.util.user.UserSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConcertViewModel @Inject constructor(
    private val stopConcertQueries: StopConcertQueries,
    userSession: UserSession,
) : ViewModel() {

    /**
     * State
     */
    var uiStateConcert: CommonResponse<Boolean> by mutableStateOf(CommonResponse.Initial())
        private set

    var avatarUrl: String = userSession.getAvatarUrl()
        private set

    fun stopConcert(documentId: String) = viewModelScope.launch {
        uiStateConcert = CommonResponse.Load()
        stopConcertQueries.stopConcert(documentId) { result ->
            uiStateConcert = if (result) {
                CommonResponse.Success(true)
            } else {
                CommonResponse.Error("Can't stop concert")
            }
        }
    }
}