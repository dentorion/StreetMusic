package com.example.streetmusic2.util.userpref

import android.content.Context
import android.content.SharedPreferences
import com.example.streetmusic2.util.userpref.extensions.set
import com.example.streetmusic2.util.userpref.extensions.toExactDouble
import com.example.streetmusic2.util.userpref.extensions.toExactFloat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSharedPreferences @Inject constructor(@ApplicationContext context: Context) {

    private val pref: SharedPreferences =
        context.getSharedPreferences(USER_SHARED_PREFERENCES_TAG, Context.MODE_PRIVATE)

    /**
     * User "listener" GET parameters
     */

    fun isMusician(): Boolean =
        pref.getBoolean("isMusician", false)

    fun getLatitude(): Double =
        pref.getFloat("latitude", 0f).toExactDouble()

    fun getLongitude(): Double =
        pref.getFloat("longitude", 0f).toExactDouble()

    fun getCity(): String? =
        pref.getString("city", "")

    fun getCountry(): String? =
        pref.getString("country", "")

    /**
     * User "listener" SET parameters
     */

    fun setIsMusician(value: Boolean) =
        pref.set("isMusician", value)

    fun setLatitude(value: Double) =
        pref.set("latitude", value.toExactFloat())

    fun setLongitude(value: Double) =
        pref.set("longitude", value.toExactFloat())

    fun setCity(value: String) =
        pref.set("city", value)

    fun setCountry(value: String) =
        pref.set("country", value)

    /**
     * User "musician" GET parameters
     */

    fun getId(): String? =
        pref.getString("id", "")

    fun getNameGroup(): String? =
        pref.getString("latitude", "")

    fun getAvatar(): String? =
        pref.getString("longitude", "")

    fun getStyleMusic(): String? =
        pref.getString("city", "")

    fun getTimeStart(): String? =
        pref.getString("country", "")

    fun getTimeStop(): String? =
        pref.getString("country", "")

    /**
     * User "musician" SET parameters
     */

    fun setId(value: String) =
        pref.set("id", value)

    fun setNameGroup(value: String) =
        pref.set("latitude", value)

    fun setAvatar(value: String) =
        pref.set("longitude", value)

    fun setStyleMusic(value: String) =
        pref.set("city", value)

    fun setTimeStart(value: String) =
        pref.set("country", value)

    fun setTimeStop(value: String) =
        pref.set("country", value)
}

private const val USER_SHARED_PREFERENCES_TAG = "User"
