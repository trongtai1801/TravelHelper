package dut.t2.travelhepler.ui.main.more.requests.wating_requests

import android.content.Context
import dut.t2.travelhelper.base.BasePresenter
import dut.t2.travelhelper.service.core.ApiClient
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.Request
import dut.t2.travelhepler.utils.Common
import dut.t2.travelhepler.utils.Constant
import dut.t2.travelhepler.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WaitingTravelRequestsPresenterImpl(context: Context) :
    BasePresenter<WaitingTravelRequestsContract.WaitingTravelRequestsView>(context),
    WaitingTravelRequestsContract.WaitingTravelRequestsPresenter {

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
                } else {
                    var message = Common.getErrorString(response)
                    if (message != "") {
                        view!!.showMessage(message)
                    } else view!!.showMessage(context.getString(R.string.can_not_get_request))
                }
                view!!.dismissLoading()
            }

            override fun onFailure(call: Call<ArrayList<Request>>, t: Throwable) {
                view!!.showMessage(t.toString())
                view!!.dismissLoading()
            }
        })
    }

    override fun acceptTravelRequest(requestId: Int) {
        var req = ApiClient.getService()!!.acceptTravelRequest(SessionManager.getAccessToken()!!, requestId)

        req.enqueue(object : Callback<Request> {
            override fun onResponse(call: Call<Request>, response: Response<Request>) {
                if (response.isSuccessful) {
                    view!!.acceptTravelRequestResult()
                } else {
                    var message = Common.getErrorString(response)
                    if (message != "") {
                        view!!.showMessage(message)
                    } else view!!.showMessage(context.getString(R.string.can_not_accept_travel_request))
                }
                view!!.dismissLoading()
            }

            override fun onFailure(call: Call<Request>, t: Throwable) {
                view!!.showMessage(t.toString())
                view!!.dismissLoading()
            }
        })
    }

    override fun ignoreTravelRequest(requestId: Int) {
        var req = ApiClient.getService()!!.ignoreTravelRequest(SessionManager.getAccessToken()!!, requestId)

        req.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.code() == Constant.REQUEST_DELETE_SUCCESS) {
                    view!!.ignoreTravelRequestResult()
                } else view!!.showMessage(context.getString(R.string.can_not_ignore_travel_request))
                view!!.dismissLoading()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                view!!.showMessage(t.toString())
                view!!.dismissLoading()
            }
        })
    }
}