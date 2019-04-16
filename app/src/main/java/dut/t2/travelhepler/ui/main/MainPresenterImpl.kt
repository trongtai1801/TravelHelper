package dut.t2.travelhepler.ui.main

import android.content.Context
import dut.t2.travelhelper.base.BasePresenter
import dut.t2.travelhelper.service.core.ApiClient
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.PublicTrip
import dut.t2.travelhepler.service.response.PublicTripsResponse
import dut.t2.travelhepler.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenterImpl(context: Context) : BasePresenter<MainContract.MainView>(context), MainContract.MainPresenter {
    override fun getPublicTrips() {
        var req = ApiClient.getService()!!.getPublicTrips(SessionManager.getAccessToken()!!)

        req.enqueue(object : Callback<ArrayList<PublicTrip>> {
            override fun onResponse(call: Call<ArrayList<PublicTrip>>, response: Response<ArrayList<PublicTrip>>) {
                if (response.isSuccessful) {
                    view!!.getPublicTripsResult(response.body()!!)
                } else {
                    view!!.dismissLoading()
                    view!!.getPublicTripsResult(null)
                    view!!.showMessage(context!!.getString(R.string.get_publictrips_error))
                }
            }

            override fun onFailure(call: Call<ArrayList<PublicTrip>>, t: Throwable) {
                view!!.dismissLoading()
                view!!.getPublicTripsResult(null)
                view!!.showMessage(t.toString())
            }
        })
    }
}