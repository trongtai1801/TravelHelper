package dut.t2.travelhepler.ui.main

import dut.t2.travelhelper.base.BaseView
import dut.t2.travelhepler.service.model.PublicTrip

interface MainContract {

    interface MainView : BaseView {
        fun getPublicTripsResult(publicTrips: ArrayList<PublicTrip>)
    }

    interface MainPresenter {
        fun getPublicTrips();
    }

}