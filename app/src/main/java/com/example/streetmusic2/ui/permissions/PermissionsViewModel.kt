package com.example.streetmusic2.ui.permissions

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.streetmusic2.common.model.Concert
import com.example.streetmusic2.common.model.MyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PermissionsViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(MyResponse.Load<Concert>())
        private set

}