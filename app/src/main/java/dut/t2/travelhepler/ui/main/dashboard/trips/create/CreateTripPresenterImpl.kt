package dut.t2.travelhepler.ui.main.dashboard.trips.create

import android.content.Context
import com.google.gson.JsonObject
import dut.t2.travelhelper.base.BasePresenter
import dut.t2.travelhelper.service.core.ApiClient
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.PublicTrip
import dut.t2.travelhepler.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateTripPresenterImpl(context: Context) : BasePresenter<CreateTripContract.CreateTripView>(context),
    CreateTripContract.CreateTripPresenter {

    override fun getSuggestAddress() {
        var req = ApiClient.getService()!!.getSuggestAddress(SessionManager.getAccessToken()!!)

        req.enqueue(object : Callback<ArrayList<String>> {
            override fun onResponse(call: Call<ArrayList<String>>, response: Response<ArrayList<String>>) {
                if (response.isSuccessful) {
                    if (response.body() != null && (response.body() as ArrayList<String>).size > 0) {
                        view!!.getSuggestAddressResult(response.body() as ArrayList<String>)
                    } else {
                        view!!.showMessage(context.getString(R.string.addresses_empty))
                    }
                } else {
                    view!!.showMessage(response.message())
                }
                view!!.dismissLoading()
            }

            override fun onFailure(call: Call<ArrayList<String>>, t: Throwable) {
                view!!.showMessage(t.toString())
                view!!.dismissLoading()
            }
        })
    }

    override fun createPublicTrip(trip: JsonObject) {
        var req = ApiClient.getService()!!.createPublicTrip(SessionManager.getAccessToken()!!, trip)
        req.enqueue(object : Callback<PublicTrip> {
            override fun onResponse(call: Call<PublicTrip>, response: Response<PublicTrip>) {
                if (response.isSuccessful && (response.body() as PublicTrip) != null)
                    view!!.createPublicTripResult(response.body() as PublicTrip)
                else
                    view!!.showMessage(response.message())
                view!!.dismissLoading()
            }

            override fun onFailure(call: Call<PublicTrip>, t: Throwable) {
                view!!.showMessage(t.toString())
                view!!.dismissLoading()
            }
        })
    }
}