package dut.t2.travelhepler.ui.main.more.friends.my_requests

import android.content.Context
import dut.t2.travelhelper.base.BasePresenter
import dut.t2.travelhelper.service.core.ApiClient
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.FriendRequest
import dut.t2.travelhepler.utils.Common
import dut.t2.travelhepler.utils.Constant
import dut.t2.travelhepler.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyFriendRequestsPresenterImpl(context: Context) :
    BasePresenter<MyFriendRequestsContract.MyFriendRequestsView>(context),
    MyFriendRequestsContract.MyFriendRequestsPresenter {

    override fun getMyFriendRequests() {
        val req = ApiClient.getService()!!.getMyFriendRequests(SessionManager.getAccessToken()!!)

        req.enqueue(object : Callback<ArrayList<FriendRequest>> {
            override fun onResponse(
                call: Call<ArrayList<FriendRequest>>,
                response: Response<ArrayList<FriendRequest>>
            ) {
                if (response.isSuccessful) {
                    val result = response.body() as ArrayList<FriendRequest>
                    for (request: FriendRequest in result) request.receiver.setDefaultValue()
                    view!!.getMyFriendRequestReuslt(result)
                } else view!!.showMessage(context.getString(R.string.can_not_get_my_requests))
                view!!.dismissLoading()
            }

            override fun onFailure(call: Call<ArrayList<FriendRequest>>, t: Throwable) {
                view!!.showMessage(t.toString())
                view!!.dismissLoading()
            }
        })
    }

    override fun cancelFriendRequest(requestId: Int) {
        val req = ApiClient.getService()!!.cancelFriendRequest(SessionManager.getAccessToken()!!, requestId)

        req.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.code() == Constant.REQUEST_DELETE_SUCCESS) {
                    view!!.cancelFriendRequestResult()
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