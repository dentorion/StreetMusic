package com.example.streetmusic2.util.firebase

import android.util.Log
import com.example.streetmusic2.common.model.domain.ConcertDomain
import com.example.streetmusic2.common.model.domain.convertToConcertDomain
import com.example.streetmusic2.util.constant.FIELD_ARTIST_ID
import com.example.streetmusic2.util.constant.FIELD_CREATE
import com.example.streetmusic2.util.constant.FIELD_STOP_MANUAL
import com.example.streetmusic2.util.firebase.model.UserFirebase
import com.example.streetmusic2.util.time.TimeUtil
import com.example.streetmusic2.util.user.UserSharedPreferences
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject
import javax.inject.Named

/**
 * Firebase queries related to Artist user
 */

@ViewModelScoped
class ArtistQueries @Inject constructor(
    @Named("concerts") private val fireBaseDbConcerts: CollectionReference,
    @Named("users") private val fireBaseDbUsers: CollectionReference,
    private val timeUtil: TimeUtil,
    private val userPref: UserSharedPreferences,
) {
    private val concertsActual = mutableListOf<ConcertDomain>()
    private val concertsExpired = mutableListOf<ConcertDomain>()

    /**
     * Check is Artist is online,
     * if yes -> get Firebase document ID of on-line concert for manual stop
     */
    suspend fun checkArtistOnline(artistId: String): String {
        var documentId = ""
        fireBaseDbConcerts
            .whereEqualTo(FIELD_ARTIST_ID, artistId)
            .whereEqualTo(FIELD_STOP_MANUAL, false)
            .whereGreaterThan(FIELD_CREATE, Date(timeUtil.getUnixNowMinusHour()))
            .get()
            .await()
            .forEach { document ->
                documentId = document.id
            }
        return documentId
    }

    /**
     * Create Artist Document by UID and count enters
     */
    fun createArtistDocument(uid: String) {
        val user = UserFirebase(
            city = userPref.getCity(),
            country = userPref.getCountry()
        )
        fireBaseDbUsers.document(uid).set(user, SetOptions.merge())
        Log.i("MyMusic", "~ 3. Create Artist Document ok.")
    }

    /**
     * Check avatar existing:
     * Yes -> Get url and save
     * No -> Save default url
     */
    suspend fun getAvatarUrl(artistId: String, callBack: (String) -> Unit) {
        Log.i("MyMusic", "Authorize VM. ArtistQueries. getAvatarUrl")

        val fileName = "$artistId.jpg"
        Log.i("MyMusic", "Authorize VM. ArtistQueries. getAvatarUrl. fileName : $fileName")

        val refStorage = FirebaseStorage.getInstance().reference.child("avatars/$fileName")
        Log.i("MyMusic", "Authorize VM. ArtistQueries. getAvatarUrl. refStorage : $refStorage")

        try {
            refStorage.downloadUrl
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.i("MyMusic", "Authorize VM. ArtistQueries. getAvatarUrl. SuccessListener")
                        Log.i("MyMusic", "=================== ${task.result}")
                        callBack(task.result.toString())
                    }
                }.await()
        } catch (exception: Exception) {
            Log.i("MyMusic", "Authorize VM. ArtistQueries. getAvatarUrl. FailureListener")
            callBack("")
        }
    }

    /**
     * Get all concerts by Artist
     * Active - first list
     * Not active - second
     */
    suspend fun getAllConcertsByArtist(artistId: String): Pair<List<ConcertDomain>, List<ConcertDomain>> {
        concertsActual.clear()
        concertsExpired.clear()
        fireBaseDbConcerts
            .whereEqualTo(FIELD_ARTIST_ID, artistId)
            .get()
            .await()
            .forEach { document ->
                /**
                 * Active concert = StartTimeFromFirebase > Now - 1 Hour
                 * Not active concert = StartTimeFromFirebase < Now - 1 Hour
                 */
                val unixStartConcertFirebase = document.getDate(FIELD_CREATE)!!
                val unixNowMinusHour = Date(timeUtil.getUnixNowMinusHour())

                if (unixStartConcertFirebase.after(unixNowMinusHour)) {
                    concertsActual.add(document.convertToConcertDomain())
                } else {
                    concertsExpired.add(document.convertToConcertDomain())
                }
            }
        return Pair(
            first = concertsActual,
            second = concertsExpired
        )
    }
}