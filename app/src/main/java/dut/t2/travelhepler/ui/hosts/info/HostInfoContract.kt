package dut.t2.travelhepler.ui.hosts.info

import dut.t2.travelhelper.base.BaseView
import dut.t2.travelhepler.service.model.Home

interface HostInfoContract {
    interface HostInfoView : BaseView {

        fun getHomeInfoResult(home: Home)

        fun checkFriendResult(isFriend: Boolean)

    }

    interface HostInfoPresenter {

        fun getHomeInfo(userId: String)

        fun checkFriend(userId: String)

    }
}