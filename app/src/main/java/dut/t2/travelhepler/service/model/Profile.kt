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

    @Ignore
    @SerializedName("learningLanguage")
    @Expose
    @Nullable
    var learningLanguage: String,

    @SerializedName("about")
    @Expose
    var about: String,

    @SerializedName("interest")
    @Expose
    var interest: String,

    @SerializedName("email")
    @Expose
    var email: String,

    @SerializedName("phoneNumber")
    @Expose
    var phoneNumber: String,

    @SerializedName("avatarLocation")
    @Expose
    var avatar: String
) : RealmModel {
    public constructor() : this("", "", "", true, "", "", "", "", "", "", "", "", "")

    fun setDefaultValue() {
        this.id = this.id ?: ""
        this.fullName = this.fullName ?: ""
        this.address = this.address ?: ""
        this.gender = this.gender ?: false
        this.birthday = this.birthday ?: ""
        this.occupation = this.occupation ?: ""
        this.fluentLanguage = this.fluentLanguage ?: ""
        this.learningLanguage = this.learningLanguage ?: ""
        this.about = this.about ?: ""
        this.interest = this.interest ?: ""
        this.email = this.email ?: ""
        this.phoneNumber = this.phoneNumber ?: ""
        this.avatar = this.avatar ?: ""
    }

    fun splitBirthday(): String {
        return this.birthday.split("T")[0]
    }
}