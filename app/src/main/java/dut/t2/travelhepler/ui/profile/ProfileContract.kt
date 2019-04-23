package dut.t2.travelhepler.ui.profile

import dut.t2.travelhelper.base.BaseView
import dut.t2.travelhelper.service.model.Profile
import okhttp3.MultipartBody

interface ProfileContract {

    interface ProfileView : BaseView {
        fun updateAvatarResult(profile: Profile)
    }

    interface ProfilePresenter {
        fun updateAvatar(avatar: MultipartBody.Part)
    }
}