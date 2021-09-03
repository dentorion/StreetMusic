package com.example.streetmusic2.util.firebase

import com.example.streetmusic2.common.model.Concert
import com.example.streetmusic2.util.constant.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.getField
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

@ViewModelScoped
class ConcertsFirebaseQueries @Inject constructor(
    private val fireBaseDb: CollectionReference
) {
    private val concerts = mutableListOf<Concert>()

    /**
     * Get actual "concerts" in city, styles = all
     */
    suspend fun getConcertsActualCity(city: String): List<Concert> {
        concerts.clear()
        fireBaseDb
            .whereEqualTo(FILED_CITY, city)
            .whereGreaterThan(FILED_STOP, Date())
            .get()
            .await()
            .forEach {
                concerts.add(toDomainModel(it))
            }
        return concerts.toList()
    }

    /**
     * Get expired "concerts" in city, styles = all
     */
    suspend fun getConcertsExpiredCity(city: String): List<Concert> {
        concerts.clear()
        fireBaseDb
            .whereEqualTo(FILED_CITY, city)
            .whereLessThan(FILED_STOP, Date())
            .get()
            .await()
            .forEach {
                concerts.add(toDomainModel(it))
            }
        return concerts.toList()
    }

    /**
     * Get actual "concerts" by city by style of music
     */
    suspend fun getConcertsActualCityStyle(city: String, style: String): List<Concert> {
        concerts.clear()
        fireBaseDb
            .whereEqualTo(FILED_CITY, city)
            .whereEqualTo(FILED_STYLE, style)
            .whereGreaterThan(FILED_STOP, Date())
            .get()
            .await()
            .forEach {
                concerts.add(toDomainModel(it))
            }
        return concerts.toList()
    }

    /**
     * Get expired "concerts" by city by style of music
     */
    suspend fun getConcertsExpiredCityStyle(city: String, style: String): List<Concert> {
        concerts.clear()
        fireBaseDb
            .whereEqualTo(FILED_CITY, city)
            .whereEqualTo(FILED_STYLE, style)
            .whereLessThan(FILED_STOP, Date())
            .get()
            .await()
            .forEach {
                concerts.add(toDomainModel(it))
            }
        return concerts.toList()
    }

    private fun toDomainModel(qds: QueryDocumentSnapshot): Concert =
        Concert(
            id = qds.getField<Int>(FILED_ID) ?: (5..1000000).random(),
            name = qds.getString(FILED_NAME_OF_GROUP) ?: "",
            city = qds.getString(FILED_CITY) ?: "",
            country = qds.getString(FILED_COUNTRY) ?: "",
            latitude = qds.getString(FILED_LATITUDE) ?: "",
            longitude = qds.getString(FILED_LONGITUDE) ?: "",
            avatar = qds.getString(FILED_AVATAR) ?: "",
            styleMusic = qds.getString(FILED_STYLE) ?: "",
            timeStart = qds.getDate(FILED_START) ?: Date(),
            timeStop = qds.getDate(FILED_STOP) ?: Date()
        )
}