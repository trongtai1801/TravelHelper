package dut.t2.travelhepler.utils

import dut.t2.travelhelper.service.model.Profile
import io.realm.Realm

class RealmDAO() {
    companion object {
        fun getInstance(): RealmDAO {
            return RealmDAO()
        }

        fun getProfileLogin(): Profile? {
            val profile = Realm.getDefaultInstance().where(Profile::class.java!!).findFirst()
            var result: Profile? = null
            if (profile != null) {
                result = Realm.getDefaultInstance().copyFromRealm(profile)
            }
            return result
        }

        fun setProfileLogin(profile: Profile) {
            Realm.getDefaultInstance().executeTransaction(Realm.Transaction { realm -> realm.copyToRealmOrUpdate(profile) })
        }
    }
}