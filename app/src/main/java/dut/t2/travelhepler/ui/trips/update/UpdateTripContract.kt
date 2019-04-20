package dut.t2.travelhepler.ui.trips.update

import dut.t2.travelhelper.base.BaseView

interface UpdateTripContract {
    interface UpdateTripView : BaseView {
        fun getSuggestAddressResult(addresses: ArrayList<String>)
    }

    interface UpdateViewPresenter {
        fun getSuggestAddress()
    }
}