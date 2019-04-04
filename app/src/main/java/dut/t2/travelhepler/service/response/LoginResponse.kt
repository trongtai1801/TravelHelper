package dut.t2.travelhelper.service.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import dut.t2.travelhelper.service.model.Profile

class LoginResponse(
    code: Int, message: String,
    @SerializedName("user")
    @Expose
    var profile: Profile,
    @SerializedName("token")
    @Expose
    var token: String
) : BaseResponse(code, message) {
}