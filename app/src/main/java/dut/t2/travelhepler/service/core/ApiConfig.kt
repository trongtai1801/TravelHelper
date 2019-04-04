package dut.t2.basemvp.service.core

import android.content.Context

data class ApiConfig(val context: Context?, val baseUrl: String?, val auth: String?) {
    data class Builder(var context: Context? = null, var baseUrl: String? = null, var auth: String? = null) {
        fun context(context: Context) = apply { this.context = context }
        fun baseUrl(baseUrl: String) = apply { this.baseUrl = baseUrl }
        fun auth(auth: String) = apply { this.auth = auth }
        fun build() = ApiConfig(context, baseUrl, auth)
    }
}