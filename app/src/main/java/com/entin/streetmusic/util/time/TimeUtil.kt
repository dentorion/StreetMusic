package com.entin.streetmusic.util.time

import android.content.Context
import com.entin.streetmusic.util.user.UserSession
import java.text.SimpleDateFormat
import java.util.*

@Suppress("unused")
class TimeUtil(context: Context) {

    private val userPref = UserSession(context)

    /**
     * Get difference VALUE between:
     * Real actual Unix time gotten from Time Server and
     * Application actual Unix Time in MILLISECONDS
     */
    fun getTimeDifferenceMillisecondsValue() = userPref.getTimeDifference()

    /**
     * Get current UNIX time without correcting with server UNIX time
     * MILLISECONDS
     */
    private fun getCurrentUnixTimeMilliseconds(): Long = Date().time

    /**
     * Get current UNIX  time with correcting with server time
     * For Concert Creation
     */
    private fun getCurrentUnixTimeMillisecondsWithDifference(): Long =
        getCurrentUnixTimeMilliseconds() + getTimeDifferenceMillisecondsValue()

    /**
     * Get string date of Start / End Concert time
     * For Concert page
     */
    fun getStringConcertTime(unixTime: Long): String =
        SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ENGLISH)
            .format(Date(unixTime))

    /**
     * Get Start time without hours of Concert
     * For Artist page
     */
    fun getStartDateString(unixTime: Long): String =
        SimpleDateFormat("dd.MM.yy", Locale.ENGLISH)
            .format(Date(unixTime))

    /**
     * Get Start time with hours of Concert
     * For Artist page
     */
    fun getStartHoursString(unixTime: Long): String =
        SimpleDateFormat("HH:mm", Locale.ENGLISH)
            .format(Date(unixTime))

    /**
     * Get Stop time only hours of Concert
     * For Artist page
     */
    fun getStopHoursString(unixTime: Long): String =
        SimpleDateFormat("HH:mm", Locale.ENGLISH)
            .format(Date(unixTime))

    /**
     * Calculate difference between:
     * Real actual Unix time gotten from Time Server and
     * Application actual Unix Time in MILLISECONDS
     */
    fun calculateDifference(serverUnixTime: Long): Long =
        serverUnixTime - getCurrentUnixTimeMilliseconds()

    /**
     * Save difference to UserPref
     */
    fun saveDifference(timeDifference: Long) {
        userPref.setTimeDifference(timeDifference)
    }

    /**
     * Now plus 1 Hour
     */
    fun getUnixNowPlusHour(): Long =
        getCurrentUnixTimeMillisecondsWithDifference() + HOUR_ONE_MLS

    /**
     * Now minus 1 Hour
     */
    fun getUnixNowMinusHour() =
        getCurrentUnixTimeMillisecondsWithDifference() - HOUR_ONE_MLS

}

/**
 * 1 Hour in mls
 */

const val HOUR_ONE_MLS: Long = 3600000L