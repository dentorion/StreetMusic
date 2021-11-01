package com.example.streetmusic.util.user

import android.content.Context
import android.content.SharedPreferences
import com.example.streetmusic.util.user.constant.*
import com.example.streetmusic.util.user.extensions.set

class UserSession(context: Context) {

    private val pref: SharedPreferences =
        context.getSharedPreferences(USER_SHARED_PREFERENCES_TAG, Context.MODE_PRIVATE)

    /**
     * User "listener" GET parameters
     */

    fun isMusician(): Boolean =
        pref.getBoolean(USER_IS_MUSICIAN, false)

    fun getLatitude(): String =
        pref.getString(USER_LATITUDE, "").toString()

    fun getLongitude(): String =
        pref.getString(USER_LONGITUDE, "").toString()

    fun getCity(): String =
        pref.getString(USER_CITY, "").toString()

    fun getCountry(): String =
        pref.getString(USER_COUNTRY, "").toString()

    fun getTimeDifference(): Long =
        pref.getLong(USER_TIME_DIFFERENCE, 0L)

    /**
     * User "listener" SET parameters
     */
    // Set up at Start Screen
    fun setIsMusician(value: Boolean) =
        pref.set(USER_IS_MUSICIAN, value)

    // Set up at Permissions Screen
    fun setLatitude(value: String) =
        pref.set(USER_LATITUDE, value)

    // Set up at Permissions Screen
    fun setLongitude(value: String) =
        pref.set(USER_LONGITUDE, value)

    // Set up at Permissions Screen
    fun setCity(value: String) =
        pref.set(USER_CITY, value)

    // Set up at Permissions Screen
    fun setCountry(value: String) =
        pref.set(USER_COUNTRY, value)

    // Set up at Permissions Screen
    fun setTimeDifference(value: Long) =
        pref.set(USER_TIME_DIFFERENCE, value)

    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * ARTIST "musician" GET parameters
     */
    fun getId(): String =
        pref.getString(USER_ID, "").toString()

    fun getBandName(): String =
        pref.getString(USER_NAME_BAND, "").toString()

    fun getAddress(): String =
        pref.getString(USER_ADDRESS, "").toString()

    fun getDescription(): String =
        pref.getString(USER_DESCRIPTION, "").toString()

    fun getStyleMusic(): String =
        pref.getString(USER_STYLE, "").toString()

    fun getTimeStop(): Long =
        pref.getLong(USER_TIME_FINISH, 0L)

    /**
     * ARTIST "musician" SET parameters
     */
    fun setId(value: String) =
        pref.set(USER_ID, value)

    fun setBandName(value: String) =
        pref.set(USER_NAME_BAND, value)

    fun setAddress(value: String) =
        pref.set(USER_ADDRESS, value)

    fun setDescription(value: String) =
        pref.set(USER_DESCRIPTION, value)

    fun setStyleMusic(value: String) =
        pref.set(USER_STYLE, value)

    fun setTimeStop(value: Long) =
        pref.set(USER_TIME_FINISH, value)
}
