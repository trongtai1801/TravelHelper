package dut.t2.travelhepler.ui.main.more.requests

import dut.t2.travelhelper.base.BaseView
import dut.t2.travelhepler.service.model.Request

interface RequestsContract {

    interface RequestsView : BaseView {
        fun getMyRequestsResult(requests: ArrayList<Request>)
    }

    interface RequestsPresenter {
        fun getMyRequests()
    }

}