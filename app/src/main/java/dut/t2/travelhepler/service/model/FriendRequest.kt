package dut.t2.travelhepler.service.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import dut.t2.travelhelper.service.model.Profile

class FriendRequest(
    @SerializedName("friendRequestId")
    @Expose
    var id: Int,

    @SerializedName("isAccepted")
    @Expose
    var isAccepted: Boolean,

    @SerializedName("isDeleted")
    @Expose
    var isDeleted: Boolean,

    @SerializedName("sender")
    @Expose
    var sender: Profile,

    @SerializedName("receiver")
    @Expose
    var receiver: Profile
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readParcelable(Profile::class.java.classLoader),
        parcel.readParcelable(Profile::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest!!.writeInt(id)
        dest.writeByte(if (isAccepted) 1 else 0)
        dest.writeByte(if (isDeleted) 1 else 0)
        dest.writeParcelable(sender, flags)
        dest.writeParcelable(receiver, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FriendRequest> {
        override fun createFromParcel(parcel: Parcel): FriendRequest {
            return FriendRequest(parcel)
        }

        override fun newArray(size: Int): Array<FriendRequest?> {
            return arrayOfNulls(size)
        }
    }
}