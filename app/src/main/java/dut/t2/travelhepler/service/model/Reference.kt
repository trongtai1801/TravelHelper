package dut.t2.travelhepler.service.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import dut.t2.travelhelper.service.model.Profile

class Reference(
    @SerializedName("referenceId")
    @Expose
    var id: Int,

    @SerializedName("content")
    @Expose
    var content: String,

    @SerializedName("status")
    @Expose
    var status: Boolean,

    @SerializedName("sender")
    @Expose
    var sender: Profile
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt() == 1,
        parcel.readParcelable(Profile::class.java!!.classLoader)
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest!!.writeInt(id)
        dest.writeString(content)
        if (status != null)
            dest.writeInt(if (status) 1 else 0)
        dest.writeParcelable(sender, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Reference> {
        override fun createFromParcel(parcel: Parcel): Reference {
            return Reference(parcel)
        }

        override fun newArray(size: Int): Array<Reference?> {
            return arrayOfNulls(size)
        }
    }
}