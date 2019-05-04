package dut.t2.travelhepler.service.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import dut.t2.travelhelper.service.model.Profile

class Request(
    @SerializedName("travelRequestId")
    @Expose
    var id: Int,

    @SerializedName("arrivalDate")
    @Expose
    var arrivalDate: String,

    @SerializedName("departureDate")
    @Expose
    var departureDate: String,

    @SerializedName("travelerNumber")
    @Expose
    var travelerNumber: Int,

    @SerializedName("isAccepted")
    @Expose
    var isAccepted: Boolean,

    @SerializedName("isDeleted")
    @Expose
    var isDeleted: Boolean,

    @SerializedName("receiver")
    @Expose
    var receiver: Profile,

    @SerializedName("sender")
    @Expose
    var sender: Profile
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readParcelable(Profile::class.java.classLoader),
        parcel.readParcelable(Profile::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest!!.writeInt(id)
        dest.writeString(arrivalDate)
        dest.writeString(departureDate)
        dest.writeInt(travelerNumber)
        dest.writeByte(if (isAccepted) 1 else 0)
        dest.writeByte(if (isDeleted) 1 else 0)
        dest.writeParcelable(receiver, flags)
        dest.writeParcelable(sender, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Request> {
        override fun createFromParcel(parcel: Parcel): Request {
            return Request(parcel)
        }

        override fun newArray(size: Int): Array<Request?> {
            return arrayOfNulls(size)
        }
    }
}