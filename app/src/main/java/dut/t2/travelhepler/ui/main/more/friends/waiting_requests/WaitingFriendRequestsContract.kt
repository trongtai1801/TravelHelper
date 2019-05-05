package dut.t2.travelhepler.ui.main.more.friends.waiting_requests

import dut.t2.travelhelper.base.BaseView
import dut.t2.travelhepler.service.model.FriendRequest

interface WaitingFriendRequestsContract {

    interface WaitingRequestsView : BaseView {
        fun getFriendRequestsResult(requests: ArrayList<FriendRequest>)
    }

    interface WaitingRequestsPresenter {
        fun getFriendRequests()
    }

}