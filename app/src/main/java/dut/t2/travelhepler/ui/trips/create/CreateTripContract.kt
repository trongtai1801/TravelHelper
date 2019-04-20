package dut.t2.travelhepler.ui.trips.create

import dut.t2.travelhelper.base.BaseView

interface CreateTripContract {

    interface CreateTripView : BaseView {
        fun getSuggestAddressResult(addresses: ArrayList<String>)
    }

    interface CreateTripPresenter {
        fun getSuggestAddress()
    }
}