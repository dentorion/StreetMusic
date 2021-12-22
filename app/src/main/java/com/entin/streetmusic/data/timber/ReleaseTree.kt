package com.entin.streetmusic.data.timber

import android.util.Log
import com.entin.streetmusic.data.firebase.errors.queries.ErrorsQueries
import com.entin.streetmusic.data.firebase.errors.model.ErrorFirebaseModel
import com.entin.streetmusic.data.user.UserSession
import com.google.firebase.crashlytics.FirebaseCrashlytics
import org.jetbrains.annotations.NotNull
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReleaseTree @Inject constructor(
    private val errorDb: ErrorsQueries,
    private val userPref: UserSession,
) : @NotNull Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val error = ErrorFirebaseModel(
            priority = priority,
            tag = tag,
            message = message,
            t = t,
            artistId = userPref.getId(),
            city = userPref.getCity(),
            country = userPref.getCountry(),
        )
        /**
         * Sending error reports to: Crashlytics and Firebase
         */
        if (priority == Log.ERROR || priority == Log.WARN) {
            // Save to Errors
            errorDb.error(error)
            // Save to Crashlytics
            FirebaseCrashlytics.getInstance().log(message)
        }
    }
}