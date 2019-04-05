package dut.t2.travelhepler.utils

import dut.t2.travelhelper.service.model.Profile
import dut.t2.travelhepler.utils.Constant.Companion.ACCESS_TOKEN

class SessionManager {
    companion object {
        private var AccessToken: String? = null
        var Profile: Profile? = null

        fun setAccessToken(accessToken: String) {
            SharedPrefs.getInstance().put(ACCESS_TOKEN, "Bearer " + accessToken)
            AccessToken = accessToken
        }

        fun getAccessToken(): String? {
            return AccessToken
        }

        fun saveProfile(profile: Profile) {
            if (profile != null) {
                RealmDAO.setProfileLogin(profile)
                Profile = profile
            }
        }

        fun init() {
            var profile: Profile? = RealmDAO.getProfileLogin()
            if (profile != null) Profile = profile
            AccessToken = ""
        }
    }
}