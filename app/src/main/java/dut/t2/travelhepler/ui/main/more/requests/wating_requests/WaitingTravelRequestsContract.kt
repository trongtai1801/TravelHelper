package dut.t2.travelhepler.ui.main.more.requests.wating_requests

import dut.t2.travelhelper.base.BaseView
import dut.t2.travelhepler.service.model.Request

interface WaitingTravelRequestsContract {

    interface WaitingTravelRequestsView : BaseView {
        fun getTravelerRequests(requests: ArrayList<Request>)

        fun acceptTravelRequestResult()

        fun ignoreTravelRequestResult()
    }

    interface WaitingTravelRequestsPresenter {
        fun getTravelerRequests()

        fun acceptTravelRequest(requestId: Int)

        fun ignoreTravelRequest(requestId: Int)
    }

}