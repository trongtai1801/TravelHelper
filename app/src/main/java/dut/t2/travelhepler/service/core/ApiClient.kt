package dut.t2.basemvp.service.core

import dut.t2.basemvp.service.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient private constructor(){

    companion object {
        private val TIME_OUT: Long = 300000

        @JvmStatic
        fun getInstance(): ApiClient? {
            return Holder.INSTANCE
        }

        fun getService(): ApiService? {
            return getInstance()!!.mApiService
        }
    }

    private var mApiService: ApiService? = null

    private object Holder { val INSTANCE = ApiClient() }

    fun init(apiConfig: ApiConfig) {
        // init OkHttpClient
        val okHttpBuilder = OkHttpClient().newBuilder()
        okHttpBuilder.connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
        // Log
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        okHttpBuilder.interceptors().add(logInterceptor)

        val retrofit = Retrofit.Builder()
            .baseUrl(apiConfig.baseUrl)
            .client(okHttpBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mApiService = retrofit.create(ApiService::class.java)
    }
}