package dut.t2.travelhepler.ui.main.more.offer.wating_offer

import dut.t2.travelhelper.base.BaseView
import dut.t2.travelhepler.service.model.Offer

interface WaitingOfferContract {

    interface WaitingOffersView : BaseView {
        fun getOfferToHostResult(offers: ArrayList<Offer>)

        fun acceptHostOfferResult()

        fun ignoreHostOffer()
    }

    interface WaitingOffersPresenter {
        fun getOfferToHost()

        fun acceptHostOffer(offerId: Int)

        fun ignoretHostOffer(offerId: Int)
    }

}