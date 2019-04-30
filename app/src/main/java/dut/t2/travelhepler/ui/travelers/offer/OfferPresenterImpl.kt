package dut.t2.travelhepler.ui.travelers.offer

import android.content.Context
import com.google.gson.JsonObject
import dut.t2.travelhelper.base.BasePresenter
import dut.t2.travelhelper.service.core.ApiClient
import dut.t2.travelhepler.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OfferPresenterImpl(context: Context) : BasePresenter<OfferContract.OfferView>(context),
    OfferContract.OfferPresenter {

    override fun createOffer(userId: String, trip: JsonObject) {
        var req = ApiClient.getService()!!.createOffer(SessionManager.getAccessToken()!!, userId, trip)

        req.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) view!!.createOfferResult()
                else view!!.showMessage(response.message())
                view!!.dismissLoading()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                view!!.showMessage(t.toString())
                view!!.dismissLoading()
            }
        })
    }
}