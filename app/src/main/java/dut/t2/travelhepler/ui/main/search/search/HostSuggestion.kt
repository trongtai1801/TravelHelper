package dut.t2.travelhepler.ui.main.search.search

import android.os.Parcel
import android.os.Parcelable
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion


class HostSuggestion(var mHostName: String, var mHistory: Boolean) : SearchSuggestion {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    )

    constructor(suggestion: String) : this(suggestion, false)

    override fun getBody(): String {
        return mHostName
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest!!.writeString(mHostName)
        dest.writeByte(if (mHistory) 1 else 0)
    }

    companion object CREATOR : Parcelable.Creator<HostSuggestion> {
        override fun createFromParcel(parcel: Parcel): HostSuggestion {
            return HostSuggestion(parcel)
        }

        override fun newArray(size: Int): Array<HostSuggestion?> {
            return arrayOfNulls(size)
        }
    }

}