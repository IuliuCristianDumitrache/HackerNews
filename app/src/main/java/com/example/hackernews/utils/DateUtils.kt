package com.example.hackernews.utils

import android.content.Context
import android.text.format.DateFormat
import com.example.hackernews.HackerNewsApplication
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.util.*

class DateUtils {
    companion object {

        private const val DAY_MONTH_ONLY = "MMM d"

        /**
         * Format date according to locale in a user friendly way.
         * Examples for Thu Oct 31 16:18:48 GMT+02:00 2019
         * output: Oct 31 2019 @ 16:18
         */
        fun formatDateUserFriendly(dateTime: DateTime?, appContext: Context): String {
            dateTime?.let {
                return formatDateWithOptionalYear(dateTime) + " @ " + formatShortTime(dateTime, appContext)
            }

            return ""
        }

        private fun formatDateWithOptionalYear(dateTime: DateTime): String {
            var pattern = DateTimeFormat.patternForStyle("M-", Locale.getDefault())
            if (dateTime.year == DateTime.now().year) {
                // current year -> show only the month and the day
                pattern = DAY_MONTH_ONLY
            }

            return DateTimeFormat.forPattern(pattern).print(dateTime)
        }

        private fun formatShortTime(dateTime: DateTime?, appContext: Context): String {
            val is24HourFormat = DateFormat.is24HourFormat(appContext)
            val pattern = if (is24HourFormat) {
                "H:mm"
            } else {
                "h:mm a"
            }

            return dateTime?.toString(pattern) ?: ""
        }
    }
}