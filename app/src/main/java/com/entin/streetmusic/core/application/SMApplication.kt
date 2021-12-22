package com.entin.streetmusic.core.application

import android.app.Application
import com.entin.streetmusic.BuildConfig
import com.entin.streetmusic.data.timber.ReleaseTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class SMApplication : Application() {

    // For timber to save logs
    @Inject
    lateinit var releaseTree: ReleaseTree

    override fun onCreate() {
        super.onCreate()

        /**
         * Timber
         */
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    return String.format(
                        "Timber. Class:%s: Line: %s, Method: %s",
                        super.createStackElementTag(element),
                        element.lineNumber,
                        element.methodName
                    )
                }
            })
        } else {
            Timber.plant(releaseTree)
        }
    }
}