package com.example.streetmusic2.ui.concert

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.streetmusic2.common.model.viewmodelstate.CommonResponse
import com.example.streetmusic2.util.firebase.StopConcertQueries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConcertViewModel @Inject constructor(
    private val stopConcertQueries: StopConcertQueries
) : ViewModel() {

    /**
     * State
     */
    var stateConcert: CommonResponse<Boolean> by mutableStateOf(CommonResponse.Initial())
        private set

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
}