package dut.t2.travelhepler.ui.main.more.requests

import android.content.Context
import dut.t2.travelhelper.base.BasePresenter
import dut.t2.travelhelper.service.core.ApiClient
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.Request
import dut.t2.travelhepler.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestsPresenterImpl(context: Context) : BasePresenter<RequestsContract.RequestsView>(context),
    RequestsContract.RequestsPresenter {

    override fun getMyRequests() {
        var req = ApiClient.getService()!!.getMyRequests(SessionManager.getAccessToken()!!)

        req.enqueue(object : Callback<ArrayList<Request>> {
            override fun onResponse(call: Call<ArrayList<Request>>, response: Response<ArrayList<Request>>) {
                if (response.isSuccessful) {
                    if ((response.body() as ArrayList<Request>) != null) {
                        if ((response.body() as ArrayList<Request>).size > 0) {
                            var results = response.body() as ArrayList<Request>
                            for (request: Request in results) request.receiver.setDefaultValue()
                            view!!.getMyRequestsResult(results)
                        } else view!!.showToast(context.getString(R.string.you_dont_have_request))
                    } else view!!.showMessage(context.getString(R.string.data_null))
                } else view!!.showMessage(context.getString(R.string.can_not_get_request))
                view!!.dismissLoading()
            }

            override fun onFailure(call: Call<ArrayList<Request>>, t: Throwable) {
                view!!.showMessage(t.toString())
                view!!.dismissLoading()
            }
        })
    }
}