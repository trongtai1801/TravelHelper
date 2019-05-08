package dut.t2.travelhepler.ui.main.more.requests.my_requests

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

class MyRequestsPresenterImpl(context: Context) : BasePresenter<MyRequestsContract.MyRequestsView>(context),
    MyRequestsContract.MyRequestsPresenter {

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

    override fun cancelMyRequest(requestId: Int) {
        var req = ApiClient.getService()!!.cancelTravelRequest(SessionManager.getAccessToken()!!, requestId)

        req.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.code() == Constant.REQUEST_DELETE_SUCCESS) {
                    view!!.cancelMyRequestResult()
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