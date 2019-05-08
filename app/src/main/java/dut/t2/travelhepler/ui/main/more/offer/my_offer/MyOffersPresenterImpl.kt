package dut.t2.travelhepler.ui.main.more.offer.my_offer

import android.content.Context
import dut.t2.travelhelper.base.BasePresenter
import dut.t2.travelhelper.service.core.ApiClient
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.Offer
import dut.t2.travelhepler.utils.Common
import dut.t2.travelhepler.utils.Constant
import dut.t2.travelhepler.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyOffersPresenterImpl(context: Context) : BasePresenter<MyOffersContract.MyOffersView>(context),
    MyOffersContract.MyOffersPresenter {

    override fun getMyOffer() {
        var req = ApiClient.getService()!!.getMyOffers(SessionManager.getAccessToken()!!)

        req.enqueue(object : Callback<ArrayList<Offer>> {
            override fun onResponse(call: Call<ArrayList<Offer>>, response: Response<ArrayList<Offer>>) {
                if (response.isSuccessful) {
                    if (response.body() as ArrayList<Offer> != null) {
                        var result = response.body() as ArrayList<Offer>
                        for (offer: Offer in result) {
                            offer.receiver?.setDefaultValue()
                            offer.sender?.setDefaultValue()
                        }
                        view!!.getMyOfferResult(result)
                    } else view!!.showToast(context.getString(R.string.data_null))
                } else view!!.showMessage(response.message())
                view!!.dismissLoading()
            }

            override fun onFailure(call: Call<ArrayList<Offer>>, t: Throwable) {
                view!!.showMessage(t.toString())
                view!!.dismissLoading()
            }
        })
    }

    override fun cancelMyOffer(requestId: Int) {
        var req = ApiClient.getService()!!.cancelHostOfferRequest(SessionManager.getAccessToken()!!, requestId)

        req.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.code() == Constant.REQUEST_DELETE_SUCCESS) {
                    view!!.cancelMyOfferRersult()
                } else {
                    var message = Common.getErrorString(response)
                    if (message != "") {
                        view!!.showMessage(message)
                    } else view!!.showMessage(context.getString(R.string.can_not_cancel_request))
                }
                view!!.dismissLoading()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                view!!.showMessage(t.toString())
                view!!.dismissLoading()
            }
        })
    }
}