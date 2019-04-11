package dut.t2.travelhepler.utils

import dut.t2.travelhelper.service.model.Profile
import io.realm.Realm

class RealmDAO {
    companion object {
        val realm = Realm.getDefaultInstance()

        fun getProfileLogin(): Profile? {
            val profile = Realm.getDefaultInstance().where(Profile::class.java!!).findFirst()
            var result: Profile? = null
            if (profile != null) {
                result = Realm.getDefaultInstance().copyFromRealm(profile)
            }
            return result
        }

        fun setProfileLogin(profile: Profile) {
            realm.executeTransaction(Realm.Transaction { realm ->
                realm.copyToRealmOrUpdate(profile)
            })
        }

        fun deleteProfileLogin() {
            realm.executeTransaction(Realm.Transaction {
                Realm.getDefaultInstance().deleteAll()
            })
        }
    }
}