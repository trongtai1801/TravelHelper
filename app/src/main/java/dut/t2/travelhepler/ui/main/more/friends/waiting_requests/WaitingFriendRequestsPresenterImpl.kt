package dut.t2.travelhepler.ui.main.more.friends.waiting_requests

import android.content.Context
import dut.t2.travelhelper.base.BasePresenter
import dut.t2.travelhelper.service.core.ApiClient
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.FriendRequest
import dut.t2.travelhepler.utils.Constant
import dut.t2.travelhepler.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WaitingFriendRequestsPresenterImpl(context: Context) :
    BasePresenter<WaitingFriendRequestsContract.WaitingRequestsView>(context),
    WaitingFriendRequestsContract.WaitingRequestsPresenter {

    override fun getFriendRequests() {
        var req = ApiClient.getService()!!.getFriendRequests(SessionManager.getAccessToken()!!)

        req.enqueue(object : Callback<ArrayList<FriendRequest>> {
            override fun onResponse(
                call: Call<ArrayList<FriendRequest>>,
                response: Response<ArrayList<FriendRequest>>
            ) {
                if (response.isSuccessful) {
                    if (response.body() as ArrayList<FriendRequest> != null) {
                        var result = response.body() as ArrayList<FriendRequest>
                        for (request: FriendRequest in result) request.sender.setDefaultValue()
                        view!!.getFriendRequestsResult(result)
                    } else view!!.showMessage(context.getString(R.string.data_null))
                } else view!!.showMessage(context.getString(R.string.can_not_get_friend_request))
                view!!.dismissLoading()
            }

            override fun onFailure(call: Call<ArrayList<FriendRequest>>, t: Throwable) {
                view!!.showMessage(t.toString())
                view!!.dismissLoading()
            }
        })
    }

    override fun acceptFriendRequest(requestId: Int) {
        var req = ApiClient.getService()!!.acceptFriendRequest(SessionManager.getAccessToken()!!, requestId)

        req.enqueue(object : Callback<FriendRequest> {
            override fun onResponse(call: Call<FriendRequest>, response: Response<FriendRequest>) {
                if (response.isSuccessful) view!!.acceptFriendRequestResult(response.body() as FriendRequest)
                else view!!.showToast(context.getString(R.string.can_not_accept_friend_request))
                view!!.dismissLoading()
            }

            override fun onFailure(call: Call<FriendRequest>, t: Throwable) {
                view!!.showMessage(t.toString())
                view!!.dismissLoading()
            }
        })
    }

    override fun ignoreFriendRequest(requestId: Int) {
        var req = ApiClient.getService()!!.ignoreFriendRequest(SessionManager.getAccessToken()!!, requestId)

        req.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.code() == Constant.REQUEST_DELETE_SUCCESS) view!!.ignoreFriendRequest()
                else view!!.showToast(context.getString(R.string.can_not_ignore_friend_request))
                view!!.dismissLoading()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                view!!.showMessage(t.toString())
                view!!.dismissLoading()
            }
        })
    }
}