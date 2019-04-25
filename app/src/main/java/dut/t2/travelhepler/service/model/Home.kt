package dut.t2.travelhepler.service.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Home(
    @SerializedName("homeId")
    @Expose
    var id: Int,

    @SerializedName("maxGuest")
    @Expose
    var maxGuest: Int,

    @SerializedName("preferedGender")
    @Expose
    var preferedGender: String,

    @SerializedName("sleepingArrangement")
    @Expose
    var sleepingArrangement: String,

    @SerializedName("sleepingDescription")
    @Expose
    var sleepingDescription: String,

    @SerializedName("transportationAccess")
    @Expose
    var transportationAccess: String,

    @SerializedName("allowedThing")
    @Expose
    var allowedThing: String,

    @SerializedName("stuff")
    @Expose
    var stuff: String,

    @SerializedName("additionInfo")
    @Expose
    var additionInfo: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest!!.writeInt(id)
        dest.writeInt(maxGuest)
        dest.writeString(preferedGender)
        dest.writeString(sleepingArrangement)
        dest.writeString(sleepingDescription)
        dest.writeString(transportationAccess)
        dest.writeString(allowedThing)
        dest.writeString(stuff)
        dest.writeString(additionInfo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Home> {
        override fun createFromParcel(parcel: Parcel): Home {
            return Home(parcel)
        }

        override fun newArray(size: Int): Array<Home?> {
            return arrayOfNulls(size)
        }
    }

    fun setDefaultValue() {
        this.id = this.id ?: 0
        this.maxGuest = this.maxGuest ?: 0
        this.preferedGender = this.preferedGender ?: ""
        this.sleepingArrangement = this.sleepingArrangement ?: ""
        this.sleepingDescription = this.sleepingDescription ?: ""
        this.transportationAccess = this.transportationAccess ?: ""
        this.allowedThing = this.allowedThing ?: ""
        this.stuff = this.stuff ?: ""
        this.additionInfo = this.additionInfo ?: ""
    }
}