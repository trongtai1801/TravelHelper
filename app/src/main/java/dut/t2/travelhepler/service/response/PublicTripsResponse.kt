package dut.t2.travelhepler.service.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import dut.t2.travelhepler.service.model.PublicTrip

class PublicTripsResponse(
    @SerializedName("publicTrips")
    @Expose
    var publicTrips: ArrayList<PublicTrip>
)