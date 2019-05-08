package dut.t2.travelhepler.ui.main.more.offer.my_offer

import dut.t2.travelhelper.base.BaseView
import dut.t2.travelhepler.service.model.Offer

interface MyOffersContract {

    interface MyOffersView : BaseView {
        fun getMyOfferResult(offers: ArrayList<Offer>)

        fun cancelMyOfferRersult()
    }

    interface MyOffersPresenter {
        fun getMyOffer()

        fun cancelMyOffer(requestId: Int)
    }
}