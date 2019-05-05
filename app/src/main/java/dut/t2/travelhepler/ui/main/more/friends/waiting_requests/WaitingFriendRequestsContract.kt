package dut.t2.travelhepler.ui.main.more.friends.waiting_requests

import dut.t2.travelhelper.base.BaseView
import dut.t2.travelhepler.service.model.FriendRequest

interface WaitingFriendRequestsContract {

    interface WaitingRequestsView : BaseView {
        fun getFriendRequestsResult(requests: ArrayList<FriendRequest>)

        fun acceptFriendRequestResult(friendRequest: FriendRequest)

        fun ignoreFriendRequest()
    }

    interface WaitingRequestsPresenter {
        fun getFriendRequests()

        fun acceptFriendRequest(requestId: Int)

        fun ignoreFriendRequest(requestId: Int)
    }

}