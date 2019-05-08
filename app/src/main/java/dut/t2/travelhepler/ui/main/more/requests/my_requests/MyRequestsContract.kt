package dut.t2.travelhepler.ui.main.more.requests.my_requests

import dut.t2.travelhelper.base.BaseView
import dut.t2.travelhepler.service.model.Request

interface MyRequestsContract {

    interface MyRequestsView : BaseView {
        fun getMyRequestsResult(requests: ArrayList<Request>)

        fun cancelMyRequestResult()
    }

    interface MyRequestsPresenter {
        fun getMyRequests()

        fun cancelMyRequest(requestId: Int)
    }

}