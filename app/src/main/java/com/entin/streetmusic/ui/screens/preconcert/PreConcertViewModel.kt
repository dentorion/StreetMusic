package com.entin.streetmusic.ui.screens.preconcert

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.entin.streetmusic.core.model.music.MusicType
import com.entin.streetmusic.core.model.response.RepoResponse
import com.entin.streetmusic.core.model.response.SMError
import com.entin.streetmusic.core.model.response.SMResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreConcertViewModel @Inject constructor(
    private val repository: PreConcertRepository,
) : ViewModel() {

    /**
     * Ui State Screen
     */
    var uiStatePreConcert: SMResponse<String> by mutableStateOf(SMResponse.InitialResponse())
        private set

    /**
     * Alert state
     */
    private val _alertState = MutableSharedFlow<AlertDialog>(replay = 0)
    val alertState: SharedFlow<AlertDialog> = _alertState

    /**
     * Fields of PreConcert screen
     */
    // Avatar url
    var avatarUrl = repository.getCurrentAvatarUrl()
        private set

    // Style
    var musicType by mutableStateOf(repository.getCurrentStyleMusic())
        private set

    // Name of Band
    var bandName by mutableStateOf(repository.getCurrentBandName())
        private set

    // Address
    var address by mutableStateOf(repository.getCurrentAddress())
        private set

    // Description
    var description by mutableStateOf(repository.getCurrentDescription())

    // City
    val city = repository.getCurrentCountry()

    // Country
    val country = repository.getCurrentCity()

    /**
     * Upload new avatar from URI
     */
    @InternalCoroutinesApi
    fun uploadNewAvatar(image: Uri?, artistId: String) = viewModelScope.launch(Dispatchers.IO) {
        uiStatePreConcert = SMResponse.LoadResponse()

        // Save image to the Firebase Storage
        if (image != null) {
            repository.uploadAvatar(image = image, artistId = artistId).collect { result ->
                when (result) {
                    is RepoResponse.ErrorResponse -> {
                        _alertState.emit(AlertDialog.AvatarError)
                        uiStatePreConcert = SMResponse.InitialResponse()
                    }
                    is RepoResponse.SuccessResponse -> {
                        avatarUrl = result.data
                        // Save avatar url to the UserSession
                        repository.setAvatarUrl(result.data)

                        uiStatePreConcert = SMResponse.InitialResponse()
                    }
                }
            }
        } else {
            uiStatePreConcert = SMResponse.ErrorResponse(SMError.ImgUrlNull)
        }
    }

    /**
     * Run Concert
     */
    fun runConcert() = viewModelScope.launch {
        uiStatePreConcert = SMResponse.LoadResponse()

        if (checkFieldsForEmpty()) {
            repository.startConcert().collect { response ->
                uiStatePreConcert = when (response) {
                    is RepoResponse.ErrorResponse -> {
                        SMResponse.ErrorResponse(SMError.ExceptionError(response.message))
                    }
                    is RepoResponse.SuccessResponse -> {
                        SMResponse.SuccessResponse(response.data)
                    }
                }
            }
        } else {
            uiStatePreConcert = SMResponse.InitialResponse()
            _alertState.emit(AlertDialog.FieldError)
        }
    }

    /**
     * Fields set up
     */

    fun setCurrentMusicType(it: MusicType) {
        repository.setCurrentMusicType(it)
        musicType = it
    }

    fun setCurrentBandName(it: String) {
        repository.setCurrentBandName(it)
        bandName = it
    }

    fun setCurrentAddress(it: String) {
        repository.setCurrentAddress(it)
        address = it
    }

    fun setCurrentDescription(it: String) {
        repository.setCurrentDescription(it)
        description = it
    }

    /**
     * Check text fields and style music are not empty
     */
    private fun checkFieldsForEmpty(): Boolean =
        bandName.isNotEmpty() && musicType.styleName.isNotEmpty() &&
                address.isNotEmpty() && description.isNotEmpty()

    /**
     * Different types of alert dialog
     */
    sealed class AlertDialog {
        object FieldError : AlertDialog()
        object AvatarError : AlertDialog()
    }
}