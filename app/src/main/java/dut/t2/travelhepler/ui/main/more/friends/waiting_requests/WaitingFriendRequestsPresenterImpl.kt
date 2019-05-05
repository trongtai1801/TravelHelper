package dut.t2.travelhepler.ui.main.more.friends.waiting_requests

import android.content.Context
import dut.t2.travelhelper.base.BasePresenter
import dut.t2.travelhelper.service.core.ApiClient
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.FriendRequest
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
}