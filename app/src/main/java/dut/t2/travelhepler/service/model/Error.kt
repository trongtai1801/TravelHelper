package dut.t2.travelhepler.service.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Error (
    @SerializedName("code")
    @Expose
    var code: String,

    @SerializedName("description")
    @Expose
    var description: String
)