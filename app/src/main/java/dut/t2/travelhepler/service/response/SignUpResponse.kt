package dut.t2.travelhepler.service.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import dut.t2.travelhepler.service.model.Error

class SignUpResponse (
    @SerializedName("succeeded")
    @Expose
    var succeeded: Boolean,

    @SerializedName("errors")
    @Expose
    var errors: List<Error>
)