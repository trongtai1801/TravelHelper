package dut.t2.travelhepler.ui.travelers.info

import dut.t2.travelhelper.base.BaseView
import dut.t2.travelhepler.service.model.Home

interface TravelerInfoContract {

    interface TravelerInfoView : BaseView {
        fun getHomeInfoResult(home: Home)

        fun checkFriendResult(isFriend: Boolean)
    }

    interface TravelerPresenter {
        fun getHomeInfo(userId: String)

        fun checkFriend(userId: String)
    }
}