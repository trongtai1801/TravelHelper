package dut.t2.travelhepler.service.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CheckFriend(
    @SerializedName("isFriend")
    @Expose
    var isFriend: Boolean
)