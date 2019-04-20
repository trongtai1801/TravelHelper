package dut.t2.travelhepler.ui.trips.create

import com.google.gson.JsonObject
import dut.t2.travelhelper.base.BaseView
import dut.t2.travelhepler.service.model.PublicTrip

interface CreateTripContract {

    interface CreateTripView : BaseView {
        fun getSuggestAddressResult(addresses: ArrayList<String>)

        fun createPublicTripResult(trip: PublicTrip)
    }

    interface CreateTripPresenter {
        fun getSuggestAddress()

        fun createPublicTrip(trip: JsonObject)
    }
}