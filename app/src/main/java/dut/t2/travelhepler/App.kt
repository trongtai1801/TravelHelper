package dut.t2.travelhepler

import android.app.Application
import dut.t2.basemvp.service.core.ApiClient
import dut.t2.basemvp.service.core.ApiConfig

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
        /*
        * start service
        */
        val apiConfig = ApiConfig.Builder()
            .baseUrl("https://api.androidhive.info/json/")
            .context(applicationContext)
            .auth("")
            .build()
        ApiClient.getInstance()!!.init(apiConfig)
    }
}