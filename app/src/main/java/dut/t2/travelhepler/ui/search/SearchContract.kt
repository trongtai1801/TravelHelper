package dut.t2.travelhepler.ui.search

import dut.t2.travelhelper.base.BaseView

interface SearchContract {

    interface SearchView : BaseView {
        fun getSuggestAddressResult(addresses: ArrayList<String>)
    }

    interface SearchPresenter {
        fun getSuggestAddress()
    }
}