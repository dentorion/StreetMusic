package com.entin.streetmusic.util.firebase.avatar.queries

import android.net.Uri
import com.entin.streetmusic.util.firebase.avatar.model.AvatarFirebaseModel
import com.entin.streetmusic.util.firebase.constant.AVATARS_NAME_HILT
import com.entin.streetmusic.util.firebase.constant.DEFAULT_URL_AVATAR
import com.entin.streetmusic.util.firebase.constant.FIELD_ARTIST_ID
import com.entin.streetmusic.util.firebase.constant.FIELD_AVATAR_URL
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

/**
 * Firebase Storage Queries
 * on checking avatar existing in storage in a case of absent file the response is exception
 */
class AvatarQueries @Inject constructor(
    @Named(AVATARS_NAME_HILT) private val fireBaseDbAvatars: CollectionReference,
) {

    /**
     * Upload new avatar to STORAGE
     */
    fun uploadAvatar(image: Uri, artistId: String, callback: (String) -> Unit) {
        val fileName = "$artistId.jpg"
        val refStorage =
            FirebaseStorage.getInstance().reference.child("avatars/$artistId/$fileName")

        refStorage.putFile(image)
            .addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                    callback(it.toString())
                    saveInFirebaseAvatarUrl(it.toString(), artistId)
                }
            }
            .addOnFailureListener {
                callback("")
            }
    }

    /**
     * Check avatar existing:
     * Yes -> Return saved url
     * No -> Return default url
     */
    suspend fun getAvatarUrl(artistId: String, callBack: (String) -> Unit) {
        var avatarUrlByArtistId: String = DEFAULT_URL_AVATAR
        fireBaseDbAvatars
            .whereEqualTo(FIELD_ARTIST_ID, artistId)
            .get()
            .await()
            .forEach { document ->
                avatarUrlByArtistId = document.getString(FIELD_AVATAR_URL) ?: DEFAULT_URL_AVATAR
            }
        callBack(avatarUrlByArtistId)
    }

    /**
     * Firebase avatars update user avatar
     */
    private fun saveInFirebaseAvatarUrl(avatarUrl: String, artistId: String) {
        val avatarModel = AvatarFirebaseModel(
            artistId = artistId,
            avatarUrl = avatarUrl,
        )
        fireBaseDbAvatars.document(artistId).set(avatarModel, SetOptions.merge())
    }
}
