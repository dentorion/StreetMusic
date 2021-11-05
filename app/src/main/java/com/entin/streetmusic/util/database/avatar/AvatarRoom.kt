package com.entin.streetmusic.util.database.avatar

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class AvatarRoom @Inject constructor(
    private val streetMusicDAO: AvatarDAO
) {
    /**
     * Check if exist avatar url with userId
     */
    suspend fun checkForAvatar(artistId: String): Boolean {
        return streetMusicDAO.checkForAvatarUrl(artistId = artistId)
    }

    /**
     * Set new avatar url for userId
     */
    suspend fun setNewAvatarUrl(avatar: AvatarModel) {
        streetMusicDAO.setNewAvatarUrl(avatar = avatar)
    }

    /**
     * Update Avatar url by userId
     */
    suspend fun updateAvatar(avatarModel: AvatarModel) {
        streetMusicDAO.updateAvatarUrl(artistId = avatarModel.artistId, url = avatarModel.avatarUrl)
    }

    /**
     * Get current avatar url by userId
     */
    suspend fun getCurrentAvatarUrl(artistId: String): AvatarModel =
        streetMusicDAO.getCurrentAvatarUrl(artistId = artistId)
}