package dut.t2.travelhelper.service.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class BaseResponse(
    @SerializedName("code")
    @Expose var
    code: Int,

    @SerializedName("message")
    @Expose
    var message: String
) {}