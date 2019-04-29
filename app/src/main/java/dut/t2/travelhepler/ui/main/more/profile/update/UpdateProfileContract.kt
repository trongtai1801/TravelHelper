package dut.t2.travelhepler.ui.main.more.profile.update

import com.google.gson.JsonObject
import dut.t2.travelhelper.base.BaseView
import dut.t2.travelhelper.service.model.Profile

interface UpdateProfileContract {

    interface UpdateProfileView : BaseView {
        fun updateProfileResult(profile: Profile)
    }

    interface UpdateProfilePresenter {
        fun updateProfile(jsonObject: JsonObject)
    }
}