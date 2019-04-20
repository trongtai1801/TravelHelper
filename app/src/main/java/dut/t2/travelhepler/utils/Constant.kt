package dut.t2.travelhepler.utils

class Constant {
    companion object {
        const val SHARE_PREFERENCES_NAME = "share_preferences_travelhelper";
        const val ACCESS_TOKEN = "access_token"

        const val INDEX_FRAGMENT_DASBOARD: Int = 0
        const val INDEX_FRAGMENT_SEARCH: Int = 1
        const val INDEX_FRAGMENT_MORE: Int = 2

        const val PUBLIC_TRIPS = "publictrips"

        const val REQUEST_CODE_CREATE_PUBLIC_TRIP: Int = 1
        const val REQUEST_CODE_UPDATE_PUBLIC_TRIP: Int = 2

        const val DATE_FORMAT_SEND = "MM.dd.yyyy"
        const val DATE_FORMAT_RECEIVE = "yyyy-MM-dd"
    }
}