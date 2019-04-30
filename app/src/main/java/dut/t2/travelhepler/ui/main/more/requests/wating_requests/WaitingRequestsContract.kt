package dut.t2.travelhepler.ui.main.more.requests.wating_requests

import dut.t2.travelhelper.base.BaseView
import dut.t2.travelhepler.service.model.Request

interface WaitingRequestsContract {

    interface WaitingRequestsView : BaseView {
        fun getTravelerRequests(requests: ArrayList<Request>)
    }

    interface WaitingRequestsPresenter {
        fun getTravelerRequests()
    }

}