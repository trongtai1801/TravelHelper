package dut.t2.travelhelper.service.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("UserName")
    @Expose
    var userName: String?,

    @SerializedName("Password")
    @Expose
    var password: String?
) {
    data class builder(var userName: String? = null, var password: String? = null) {
        fun userName(userName: String) = this.apply { this.userName = userName }
        fun password(password: String) = this.apply { this.password = password }
        fun build() = User(this.userName, this.password)
    }
}