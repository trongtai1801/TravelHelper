package dut.t2.travelhelper.service.model

import android.support.annotation.Nullable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmModel
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class  Profile constructor(
    @PrimaryKey
    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("fullName")
    @Expose
    @Nullable
    var fullName: String,

    @SerializedName("address")
    @Expose
    @Nullable
    var address: String,

    @SerializedName("gender")
    @Expose
    @Nullable
    var gender: Boolean,

    @SerializedName("birthday")
    @Expose
    @Nullable
    var birthday: String,

    @SerializedName("occupation")
    @Expose
    @Nullable
    var occupation: String,

    @Ignore
    @SerializedName("fluentLanguage")
    @Expose
    @Nullable
    var fluentLanguage: String,

    @SerializedName("email")
    @Expose
    var email: String,

    @SerializedName("phoneNumber")
    @Expose
    var phoneNumber: String
) : RealmModel {
    public constructor() : this("", "", "", true, "", "", "", "", "")

    fun setDefaultValue() {
        if (id == null) id = ""
        if (fullName == null) fullName = ""
        if (address == null) address = ""
        if (gender == null) gender = false
        if (birthday == null) birthday = ""
        if (occupation == null) occupation = ""
        if (fluentLanguage == null) fluentLanguage = ""
        if (email == null) email = ""
        if (phoneNumber == null) phoneNumber = ""
    }
}