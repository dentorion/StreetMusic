package com.example.streetmusic2.util.userpref

import android.content.Context
import android.content.SharedPreferences
import com.example.streetmusic2.util.constant.*
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
        pref.getBoolean(USER_IS_MUSICIAN, false)

    fun getLatitude(): String =
        pref.getFloat(USER_LATITUDE, 0f).toExactDouble().toString()

    fun getLongitude(): String =
        pref.getFloat(USER_LONGITUDE, 0f).toExactDouble().toString()

    fun getCity(): String =
        pref.getString(USER_CITY, "").toString()

    fun getCountry(): String =
        pref.getString(USER_COUNTRY, "").toString()

    /**
     * User "listener" SET parameters
     */
    fun setIsMusician(value: Boolean) =
        pref.set(USER_IS_MUSICIAN, value)

    fun setLatitude(value: String) =
        pref.set(USER_LATITUDE, value.toDouble().toExactFloat())

    fun setLongitude(value: String) =
        pref.set(USER_LONGITUDE, value.toDouble().toExactFloat())

    fun setCity(value: String) =
        pref.set(USER_CITY, value)

    fun setCountry(value: String) =
        pref.set(USER_COUNTRY, value)

    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * ARTIST "musician" GET parameters
     */
    fun getId(): String =
        pref.getString(USER_ID, "").toString()

    fun getNameGroup(): String =
        pref.getString(USER_NAME_BAND, "").toString()

    fun getAddress(): String =
        pref.getString(USER_ADDRESS, "").toString()

    fun getAvatar(): String =
        pref.getString(USER_AVATAR, "").toString()

    fun getDescription(): String =
        pref.getString(USER_DESCRIPTION, "").toString()

    fun getStyleMusic(): String =
        pref.getString(USER_STYLE, "").toString()

    fun getTimeStart(): Long =
        pref.getLong(USER_TIME_START, 0L)

    fun getTimeStop(): Long =
        pref.getLong(USER_TIME_FINISH, 0L)

    /**
     * ARTIST "musician" SET parameters
     */
    fun setId(value: String) =
        pref.set(USER_ID, value)

    fun setNameGroup(value: String) =
        pref.set(USER_NAME_BAND, value)

    fun setAddress(value: String) =
        pref.set(USER_ADDRESS, value)

    fun setAvatar(value: String) =
        pref.set(USER_AVATAR, value)

    fun setDescription(value: String) =
        pref.set(USER_DESCRIPTION, value)

    fun setStyleMusic(value: String) =
        pref.set(USER_STYLE, value)

    fun setTimeStart(value: Long) =
        pref.set(USER_TIME_START, value)

    fun setTimeStop(value: Long) =
        pref.set(USER_TIME_FINISH, value)
}

private const val USER_SHARED_PREFERENCES_TAG = "User"
