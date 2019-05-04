package dut.t2.travelhepler.ui.main.more.friends

import dut.t2.travelhelper.base.BaseView
import dut.t2.travelhelper.service.model.Profile

interface FriendsContract {

    interface FriendsView : BaseView {
        fun getFriendsResult(friends: ArrayList<Profile>)
    }

    interface FriendsPresenter {
        fun getFriends()
    }

}