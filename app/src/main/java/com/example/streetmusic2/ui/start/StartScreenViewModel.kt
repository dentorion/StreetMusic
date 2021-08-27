package com.example.streetmusic2.ui.start

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class StartScreenViewModel @Inject constructor(): ViewModel() {

    private val _state = MutableStateFlow<Int>(1)
    val state = _state.asStateFlow()

}