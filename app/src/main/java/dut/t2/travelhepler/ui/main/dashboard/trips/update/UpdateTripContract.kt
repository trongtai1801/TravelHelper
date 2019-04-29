package dut.t2.travelhepler.ui.main.dashboard.trips.update

import com.google.gson.JsonObject
import dut.t2.travelhelper.base.BaseView
import dut.t2.travelhepler.service.model.PublicTrip

interface UpdateTripContract {
    interface UpdateTripView : BaseView {
        fun getSuggestAddressResult(addresses: ArrayList<String>)

        fun updatePublicTripResult(trip: PublicTrip)
    }

    interface UpdateViewPresenter {
        fun getSuggestAddress()

        fun updatePublicTrip(id: Int, trip: JsonObject)
    }
}