package com.entin.streetmusic.util.firebase

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Firebase Storage Queries
 * on checking avatar existing in storage in a case of absent file the response is exception
 */
@ViewModelScoped
class AvatarStorageQueries @Inject constructor() {

    /**
     * Upload new avatar
     */
    fun uploadAvatar(image: Uri, artistId: String, callback: (String) -> Unit) {
        val fileName = "$artistId.jpg"
        val refStorage =
            FirebaseStorage.getInstance().reference.child("avatars/$artistId/$fileName")

        refStorage.putFile(image)
            .addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                    callback(it.toString())
                }
            }
            .addOnFailureListener {
                callback("")
            }
    }

    /**
     * Check avatar existing:
     * Yes -> Get url and save
     * No -> Save default url
     */
    suspend fun getAvatarUrl(artistId: String, callBack: (String) -> Unit) {
        val fileName = "$artistId.jpg"
        val refStorage = FirebaseStorage.getInstance().reference.child("avatars/$artistId/$fileName")
        try {
            refStorage.downloadUrl
                .addOnSuccessListener { url -> callBack(url.toString()) }
                .await()
        } catch (exception: Exception) {
            callBack("")
        }
    }
}