package com.entin.streetmusic.core.model.dialog

import com.entin.streetmusic.R

sealed class DialogType(val icon: Int, val title: Int)

class PermissionsDialog(
    iconDefault: Int = R.drawable.ic_marker_location,
    title: Int = R.string.location_permissions,
) : DialogType(iconDefault, title)

class MapObserveDialog(
    iconDefault: Int = R.drawable.ic_marker_location,
    title: Int = R.string.dialog_map,
) : DialogType(iconDefault, title)

class PreConcertFieldDialog(
    iconDefault: Int = R.drawable.ic_fields_error,
    title: Int = R.string.dialog_title_preconcert,
) : DialogType(iconDefault, title)

class PreConcertAvatarDialog(
    iconDefault: Int = R.drawable.ic_avatar_error,
    title: Int = R.string.dialog_title_preconcert,
) : DialogType(iconDefault, title)