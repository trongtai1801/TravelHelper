package dut.t2.travelhepler.ui.main.more.requests.wating_requests

import android.content.Context
import dut.t2.travelhelper.base.BasePresenter
import dut.t2.travelhelper.service.core.ApiClient
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.Request
import dut.t2.travelhepler.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WaitingRequestsPresenterImpl(context: Context) :
    BasePresenter<WaitingRequestsContract.WaitingRequestsView>(context),
    WaitingRequestsContract.WaitingRequestsPresenter {

    override fun getTravelerRequests() {
        var req = ApiClient.getService()!!.getRequestsToStay(SessionManager.getAccessToken()!!)

        req.enqueue(object : Callback<ArrayList<Request>> {
            override fun onResponse(call: Call<ArrayList<Request>>, response: Response<ArrayList<Request>>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        if ((response.body() as ArrayList<Request>).size > 0) {
                            var result = response.body() as ArrayList<Request>
                            for (request: Request in result) request.sender.setDefaultValue()
                            view!!.getTravelerRequests(result)
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