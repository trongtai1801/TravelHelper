package dut.t2.travelhepler.service.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import dut.t2.travelhelper.service.model.Profile

class Offer(
    @SerializedName("id")
    @Expose
    var id: Int,

    @SerializedName("arrivalDate")
    @Expose
    var arrivalDate: String,

    @SerializedName("departureDate")
    @Expose
    var departureDate: String,

    @SerializedName("isAccepted")
    @Expose
    var isAccepted: Boolean,

    @SerializedName("sender")
    @Expose
    var sender: Profile?,

    @SerializedName("receiver")
    @Expose
    var receiver: Profile?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readParcelable(Profile::class.java.classLoader),
        parcel.readParcelable(Profile::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest!!.writeInt(id)
        dest.writeString(arrivalDate)
        dest.writeString(departureDate)
        dest.writeByte(if (isAccepted) 1 else 0)
        dest.writeParcelable(sender, flags)
        dest.writeParcelable(receiver, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Offer> {
        override fun createFromParcel(parcel: Parcel): Offer {
            return Offer(parcel)
        }

        override fun newArray(size: Int): Array<Offer?> {
            return arrayOfNulls(size)
        }
    }

}