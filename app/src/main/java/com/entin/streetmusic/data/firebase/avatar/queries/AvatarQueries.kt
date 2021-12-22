package com.entin.streetmusic.data.firebase.avatar.queries

import android.net.Uri
import com.entin.streetmusic.core.model.response.RepoResponse
import com.entin.streetmusic.data.firebase.avatar.model.AvatarFirebaseModel
import com.entin.streetmusic.data.firebase.constant.AVATARS_NAME_HILT
import com.entin.streetmusic.data.firebase.constant.DEFAULT_URL_AVATAR
import com.entin.streetmusic.data.firebase.constant.FIELD_ARTIST_ID
import com.entin.streetmusic.data.firebase.constant.FIELD_AVATAR_URL
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
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
     * Firebase uses KTX library, It means that there is no sense to wrap in callback flow,
     * and can be used like normal suspend function, but this is practice on pet - project
     */
    @ExperimentalCoroutinesApi
    fun uploadAvatar(image: Uri, artistId: String): Flow<RepoResponse<String>> =
        callbackFlow {
            withContext(Dispatchers.IO) {
                val refStorage = FirebaseStorage.getInstance().reference
                    .child("$AVATARS_NAME_HILT/$artistId/$artistId.jpg")

                refStorage.putFile(image)
                    .addOnSuccessListener { taskSnapshot ->
                        taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                            saveInFirebaseAvatarUrl(it.toString(), artistId)
                            trySend(RepoResponse.SuccessResponse(it.toString()))
                        }
                    }
                    .addOnFailureListener {
                        trySend(RepoResponse.ErrorResponse(it.toString()))
                    }
            }
            awaitClose{ this.cancel() }
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