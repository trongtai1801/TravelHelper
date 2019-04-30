package dut.t2.travelhepler.utils

class Constant {
    companion object {
        const val SHARE_PREFERENCES_NAME = "share_preferences_travelhelper";
        const val ACCESS_TOKEN = "access_token"

        const val INDEX_FRAGMENT_DASBOARD: Int = 0
        const val INDEX_FRAGMENT_SEARCH: Int = 1
        const val INDEX_FRAGMENT_MORE: Int = 2

        const val PUBLIC_TRIPS = "publictrips"
        const val PHOTO = "photo"
        const val HOME = "home"
        const val HOST = "host"
        const val HOSTS = "hosts"
        const val TRAVELER = "traveler"
        const val TRAVELERS = "travelers"
        const val SEARCH_HOST_STRING = "search_host"
        const val USER_ID = "user_id"

        const val REQUEST_CODE_CREATE_PUBLIC_TRIP: Int = 1
        const val REQUEST_CODE_UPDATE_PUBLIC_TRIP: Int = 2
        const val REQUEST_CODE_UPDATE_USER_AVATAR: Int = 3
        const val REQUEST_CODE_UPDATE_USER_PROFILE: Int = 4
        const val REQUEST_CODE_GET_SEARCH_HOST_STRING: Int = 5
        const val REQUEST_CODE_WRITE_REFERENCE: Int = 6

        const val DATE_FORMAT_SEND = "MM.dd.yyyy"
        const val DATE_FORMAT_RECEIVE = "yyyy-MM-dd"

        const val ID_SEARCH_ITEM_HOST = 1
        const val ID_SEARCH_ITEM_TRAVELERS = 2
        const val ID_SEARCH_ITEM_EVENT = 3

        const val REQUEST_CODE_PICK_IMAGE = 101

        const val REQUEST_DELETE_SUCCESS = 204

        const val HOST_FLAG_SET_FRAGMENT = 1
        const val HOST_FLAG_SHOW_LIST_HOST = 2
    }
}