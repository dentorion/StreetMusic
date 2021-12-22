package com.entin.streetmusic.ui.screens.concert

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.entin.streetmusic.core.model.response.SMError
import com.entin.streetmusic.core.model.response.SMResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConcertViewModel @Inject constructor(
    private val repository: ConcertRepository,
) : ViewModel() {

    /**
     * State
     */
    var uiStateConcert: SMResponse<Boolean> by mutableStateOf(SMResponse.InitialResponse())
        private set

    var avatarUrl: String = repository.getUserSession().getAvatarUrl()
        private set

    fun stopConcert(documentId: String) = viewModelScope.launch {
        uiStateConcert = SMResponse.LoadResponse()
        repository.stopConcert(documentId) { result ->
            uiStateConcert = if (result) {
                SMResponse.SuccessResponse(true)
            } else {
                SMResponse.ErrorResponse(SMError.FailedStopConcert)
            }
        }
    }
}