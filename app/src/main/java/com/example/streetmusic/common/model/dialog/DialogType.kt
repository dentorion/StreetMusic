package com.example.streetmusic.common.model.dialog

import com.example.streetmusic.R

sealed class DialogType(val icon: Int) {
    class Permissions(icon: Int = R.drawable.ic_marker_location) : DialogType(icon)

    class MapObserve(icon: Int = R.drawable.logo) : DialogType(icon)
}