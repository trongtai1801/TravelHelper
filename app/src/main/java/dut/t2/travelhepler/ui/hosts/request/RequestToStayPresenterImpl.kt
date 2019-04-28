package dut.t2.travelhepler.ui.hosts.request

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

class RequestToStayPresenterImpl(context: Context) : BasePresenter<RequestToStayContract.RequestToStayView>(context),
    RequestToStayContract.RequestToStayPresenter {

    override fun createRequestToStay(hostId: String, trip: JsonObject) {
        var req = ApiClient.getService()!!.createRequestToStay(SessionManager.getAccessToken()!!, hostId, trip)

        req.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) view!!.createRequestToStayResult()
                else view!!.showMessage(context.getString(R.string.can_not_create_equest))
                view!!.dismissLoading()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                view!!.showMessage(t.toString())
                view!!.dismissLoading()
            }
        })
    }
}