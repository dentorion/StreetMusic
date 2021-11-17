package com.entin.streetmusic.ui.concert

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.entin.streetmusic.common.model.response.StreetMusicResponse
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
    var uiStateConcert: StreetMusicResponse<Boolean> by mutableStateOf(StreetMusicResponse.Initial())
        private set

    var avatarUrl: String = repository.getUserSession().getAvatarUrl()
        private set

    fun stopConcert(documentId: String) = viewModelScope.launch {
        uiStateConcert = StreetMusicResponse.Load()
        repository.stopConcert(documentId) { result ->
            uiStateConcert = if (result) {
                StreetMusicResponse.Success(true)
            } else {
                StreetMusicResponse.Error("Can't stop concert")
            }
        }
    }
}