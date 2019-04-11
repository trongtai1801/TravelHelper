package dut.t2.travelhepler.service.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PublicTrip(
    @SerializedName("ArrivalDate")
    @Expose
    var arrivalDate: String,

    @SerializedName("DepartureDate")
    @Expose
    var departureDate: String,

    @SerializedName("Destination")
    @Expose
    var destination: String,

    @SerializedName("TravelerNumber")
    @Expose
    var travelerNumber: Int
)