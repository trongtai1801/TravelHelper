package dut.t2.travelhepler.ui.main.more.requests.wating_requests

import dut.t2.travelhelper.base.BaseView
import dut.t2.travelhepler.service.model.Request

interface WaitingRequestsContract {

    interface WaitingRequestsView : BaseView {
        fun getTravelerRequests(requests: ArrayList<Request>)

        fun acceptTravelRequestResult()

        fun ignoreTravelRequestResult()
    }

    interface WaitingRequestsPresenter {
        fun getTravelerRequests()

        fun acceptTravelRequest(requestId: Int)

        fun ignoreTravelRequest(requestId: Int)
    }

}