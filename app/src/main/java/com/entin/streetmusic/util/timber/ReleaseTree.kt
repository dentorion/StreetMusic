package com.entin.streetmusic.util.timber

import android.util.Log
import com.entin.streetmusic.common.constants.ERROR_NAME_HILT
import com.entin.streetmusic.util.timber.model.ErrorModel
import com.entin.streetmusic.util.user.UserSession
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.SetOptions
import org.jetbrains.annotations.NotNull
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class ReleaseTree @Inject constructor(
    @Named(ERROR_NAME_HILT) private val fireBaseDbErrors: CollectionReference,
    private val userPref: UserSession,
) : @NotNull Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val error = ErrorModel(
            priority = priority,
            tag = tag,
            message = message,
            t = t,
            uId = userPref.getId(),
            city = userPref.getCity(),
            country = userPref.getCountry(),
        )
        /**
         * Sending error reports to: Crashlytics and Firebase
         */
        if (priority == Log.ERROR || priority == Log.WARN) {
            // Save to Errors
            fireBaseDbErrors.document().set(error, SetOptions.merge())
            // Save to Crashlytics
            FirebaseCrashlytics.getInstance().log(message)
        }
    }
}