package dut.t2.travelhepler.utils

import java.text.SimpleDateFormat
import java.util.*

class CalendarUtils {

    companion object {

        /**
         * Convert from a string that have format is yyyy-MM-dd to Calendar object
         *
         * @param str string that have format is yyyy-MM-dd
         *
         * */

        fun convertStringToCalendar(str: String): Calendar {
            var sdf = SimpleDateFormat(Constant.DATE_FORMAT_RECEIVE, Locale.US)
            var c = Calendar.getInstance()
            c.time = sdf.parse(str)
            return c
        }

        /**
         * Convert Calendar object to string that have format is MM.dd.yyyy
         *
         * */

        fun convertCalendarToString(c: Calendar?): String {
            var sdf = SimpleDateFormat(Constant.DATE_FORMAT_SEND, Locale.US)
            return sdf.format(c!!.time)
        }

        /**
         * Convert from a string that have format is yyyy-MM-dd to string that have format is MM.dd.yyyy
         *
         * @param str string that have format is yyyy-MM-dd
         *
         * */

        fun convertStringFormat(str: String): String {
            var c = convertStringToCalendar(str)
            return convertCalendarToString(c)
        }
    }

}