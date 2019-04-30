package dut.t2.travelhepler.ui.main

import dut.t2.travelhelper.base.BaseView
import dut.t2.travelhelper.service.model.Profile
import dut.t2.travelhepler.service.model.PublicTrip

interface MainContract {

    interface MainView : BaseView {
        fun getPublicTripsResult(publicTrips: ArrayList<PublicTrip>?)

        fun deletePublicTripResult()

        fun getHostsResult(hosts: ArrayList<Profile>)

        fun getTravelersResult(travelers: ArrayList<PublicTrip>)
    }

    interface MainPresenter {
        fun getPublicTrips()

        fun deletePublicTrip(id: Int)

        fun getHosts(address: String)

        fun getTravelers(address: String)
    }

}