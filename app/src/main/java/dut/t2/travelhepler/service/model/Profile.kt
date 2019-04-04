package dut.t2.travelhelper.service.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import javax.annotation.Nullable

@RealmClass
open class  Profile constructor(
    @PrimaryKey
    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("fullName")
    @Expose
    var fullName: String,

    @SerializedName("address")
    @Expose
    var address: String,

    @SerializedName("gender")
    @Expose
    var gender: Boolean,

    @SerializedName("birthday")
    @Expose
    var birthday: String,

    @SerializedName("occupation")
    @Expose
    var occupation: String,

    @SerializedName("fluentLanguage")
    @Expose
    var fluentLanguage: String,

    @SerializedName("email")
    @Expose
    var email: String

//    @SerializedName("phoneNumber")
//    @Expose
//    var phoneNumber: String
) : RealmObject() {
    public constructor() : this("", "", "", true, "", "", "", "")//, "")
}