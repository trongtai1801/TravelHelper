package dut.t2.travelhepler.ui.hosts.request

import com.google.gson.JsonObject
import dut.t2.travelhelper.base.BaseView

interface RequestToStayContract {

    interface RequestToStayView : BaseView {
        fun createRequestToStayResult()
    }

    interface RequestToStayPresenter {
        fun createRequestToStay(hostId: String, trip: JsonObject)
    }

}