package dut.t2.travelhepler.ui.main.more.offer.wating_offer

import android.content.Context
import dut.t2.travelhelper.base.BasePresenter
import dut.t2.travelhelper.service.core.ApiClient
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.Offer
import dut.t2.travelhepler.utils.Common
import dut.t2.travelhepler.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WaitingOffersPresenterImpl(context: Context) :
    BasePresenter<WaitingOfferContract.WaitingOffersView>(context),
    WaitingOfferContract.WaitingOffersPresenter {

    override fun getOfferToHost() {
        var req = ApiClient.getService()!!.getOfferToHost(SessionManager.getAccessToken()!!)

        req.enqueue(object : Callback<ArrayList<Offer>> {
            override fun onResponse(call: Call<ArrayList<Offer>>, response: Response<ArrayList<Offer>>) {
                if (response.isSuccessful) {
                    if (response.body() as ArrayList<Offer> != null) {
                        var result = response.body() as ArrayList<Offer>
                        for (of: Offer in result) of.sender!!.setDefaultValue()
                        view!!.getOfferToHostResult(result)
                    } else view!!.showMessage(context.getString(R.string.data_null))
                } else view!!.showMessage(Common.getErrorString(response))
                view!!.dismissLoading()
            }

            override fun onFailure(call: Call<ArrayList<Offer>>, t: Throwable) {
                view!!.showMessage(t.toString())
                view!!.dismissLoading()
            }
        })
    }
}