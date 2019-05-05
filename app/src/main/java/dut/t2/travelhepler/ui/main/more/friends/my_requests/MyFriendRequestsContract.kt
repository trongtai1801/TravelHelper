package dut.t2.travelhepler.ui.main.more.friends.my_requests

import dut.t2.travelhelper.base.BaseView
import dut.t2.travelhepler.service.model.FriendRequest

interface MyFriendRequestsContract {

    interface MyFriendRequestsView : BaseView {
        fun getMyFriendRequestReuslt(requests: ArrayList<FriendRequest>)
    }

    interface MyFriendRequestsPresenter {
        fun getMyFriendRequests()
    }

}