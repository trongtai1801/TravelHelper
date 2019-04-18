package dut.t2.travelhepler.service.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PublicTrip(
    @SerializedName("publicTripId")
    @Expose
    var id: Int,

    @SerializedName("arrivalDate")
    @Expose
    var arrivalDate: String,

    @SerializedName("departureDate")
    @Expose
    var departureDate: String,

    @SerializedName("destination")
    @Expose
    var destination: String,

    @SerializedName("travelerNumber")
    @Expose
    var travelerNumber: Int,

    @SerializedName("description")
    @Expose
    var description: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest!!.writeInt(id)
        dest.writeString(arrivalDate)
        dest.writeString(departureDate)
        dest.writeString(destination)
        dest.writeInt(travelerNumber)
        dest.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PublicTrip> {
        override fun createFromParcel(parcel: Parcel): PublicTrip {
            return PublicTrip(parcel)
        }

        override fun newArray(size: Int): Array<PublicTrip?> {
            return arrayOfNulls(size)
        }
    }

}