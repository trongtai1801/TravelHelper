package dut.t2.travelhepler.ui.main

import android.content.Context
import dut.t2.travelhelper.base.BasePresenter
import dut.t2.travelhelper.service.core.ApiClient
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.response.PublicTripsResponse
import dut.t2.travelhepler.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenterImpl(context: Context) : BasePresenter<MainContract.MainView>(context), MainContract.MainPresenter {
    override fun getPublicTrips() {
        var req = ApiClient.getService()!!.getPublicTrips(SessionManager.getAccessToken()!!)

        req.enqueue(object : Callback<PublicTripsResponse> {
            override fun onResponse(call: Call<PublicTripsResponse>, response: Response<PublicTripsResponse>) {
                if (response.isSuccessful) {
                    view!!.getPublicTripsResult(response.body()!!.publicTrips)
                } else {
                    view!!.dismissLoading()
                    view!!.showMessage(context!!.getString(R.string.get_publictrips_error))
                }
            }

            override fun onFailure(call: Call<PublicTripsResponse>, t: Throwable) {
                view!!.dismissLoading()
                view!!.showMessage(t.toString())
            }
        })
    }
}