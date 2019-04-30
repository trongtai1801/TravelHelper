package dut.t2.travelhepler.ui.travelers.offer

import com.google.gson.JsonObject
import dut.t2.travelhelper.base.BaseView

interface OfferContract {

    interface OfferView : BaseView {
        fun createOfferResult()
    }

    interface OfferPresenter {
        fun createOffer(userId: String, trip: JsonObject)
    }
}