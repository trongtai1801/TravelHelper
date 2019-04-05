package dut.t2.travelhelper.service.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class  Profile constructor(
    @PrimaryKey
    @SerializedName("id")
    @Expose
    var id: String,

    @Ignore
    @SerializedName("fullName")
    @Expose
    var fullName: String,

    @Ignore
    @SerializedName("address")
    @Expose
    var address: String,

    @Ignore
    @SerializedName("gender")
    @Expose
    var gender: Boolean,

    @Ignore
    @SerializedName("birthday")
    @Expose
    var birthday: String,

    @Ignore
    @SerializedName("occupation")
    @Expose
    var occupation: String,

    @Ignore
    @SerializedName("fluentLanguage")
    @Expose
    var fluentLanguage: String,

    @Ignore
    @SerializedName("email")
    @Expose
    var email: String,

    @Ignore
    @SerializedName("phoneNumber")
    @Expose
    var phoneNumber: String
) : RealmObject() {
    public constructor() : this("", "", "", true, "", "", "", "", "")
}