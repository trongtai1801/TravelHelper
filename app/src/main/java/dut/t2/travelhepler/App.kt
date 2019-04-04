package dut.t2.travelhepler

import android.app.Application
import dut.t2.travelhelper.service.core.ApiClient
import dut.t2.travelhelper.service.core.ApiConfig
import io.realm.Realm
import io.realm.RealmConfiguration

class App : Application() {
    companion object {
        private lateinit var sInstance: App

        @JvmStatic
        fun getInstance(): App {
            return Holder.INSTANCE
        }
    }

    private object Holder {
        val INSTANCE = App()
    }

    override fun onCreate() {
        super.onCreate()

        //realm
        Realm.init(this)
        val realmConfiguration = RealmConfiguration.Builder()
            .modules(Realm.getDefaultModule())
            .name(Realm.DEFAULT_REALM_NAME)
            .schemaVersion(0)
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(realmConfiguration)

        /*
        * start service
        */
        val apiConfig = ApiConfig.Builder()
            .baseUrl("https://travelhelperwebsite.azurewebsites.net/api/")
            .context(applicationContext)
            .build()
        ApiClient.getInstance()!!.init(apiConfig)
    }
}