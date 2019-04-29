package dut.t2.travelhepler.ui.main.more.profile

import dut.t2.travelhelper.base.BaseView
import dut.t2.travelhelper.service.model.Profile
import dut.t2.travelhepler.service.model.Home
import okhttp3.MultipartBody

interface ProfileContract {

    interface ProfileView : BaseView {
        fun updateAvatarResult(profile: Profile)

        fun getHomeInfoResult(home: Home)
    }

    interface ProfilePresenter {
        fun updateAvatar(avatar: MultipartBody.Part)

        fun getHomeInfo()
    }
}