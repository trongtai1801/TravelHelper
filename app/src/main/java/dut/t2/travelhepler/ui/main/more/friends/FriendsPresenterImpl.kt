package dut.t2.travelhepler.ui.main.more.friends

import android.content.Context
import dut.t2.travelhelper.base.BasePresenter
import dut.t2.travelhelper.service.core.ApiClient
import dut.t2.travelhelper.service.model.Profile
import dut.t2.travelhepler.R
import dut.t2.travelhepler.utils.Common
import dut.t2.travelhepler.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FriendsPresenterImpl(context: Context) : BasePresenter<FriendsContract.FriendsView>(context),
    FriendsContract.FriendsPresenter {

    override fun getFriends() {
        val req = ApiClient.getService()!!.getFriends(SessionManager.getAccessToken()!!)

        req.enqueue(object : Callback<ArrayList<Profile>> {
            override fun onResponse(call: Call<ArrayList<Profile>>, response: Response<ArrayList<Profile>>) {
                if (response.isSuccessful) {
                    if (response.body() as ArrayList<Profile> != null) {
                        var result = response.body() as ArrayList<Profile>
                        for (friend: Profile in result) friend.setDefaultValue()
                        view!!.getFriendsResult(result)
                    } else view!!.showMessage(context.getString(R.string.data_null))
                } else {
                    var message = Common.getErrorString(response)
                    if (message != "") {
                        view!!.showMessage(message)
                    } else view!!.showMessage(context.getString(R.string.can_not_get_friends))
                }
                view!!.dismissLoading()
            }

            override fun onFailure(call: Call<ArrayList<Profile>>, t: Throwable) {
                view!!.showMessage(t.toString())
                view!!.dismissLoading()
            }
        })
    }

    override fun searchFriend(searchString: String) {
        var req = ApiClient.getService()!!.searchUser(SessionManager.getAccessToken()!!, searchString)

        req.enqueue(object : Callback<ArrayList<Profile>> {
            override fun onResponse(call: Call<ArrayList<Profile>>, response: Response<ArrayList<Profile>>) {
                if (response.isSuccessful) {
                    if (response.body() as ArrayList<Profile> != null) {
                        var result = response.body() as ArrayList<Profile>
                        for (user: Profile in result) user.setDefaultValue()
                        view!!.searchFriendResult(result)
                    } else view!!.showMessage(context.getString(R.string.data_null))
                } else {
                    var message = Common.getErrorString(response)
                    if (message != "") {
                        view!!.showMessage(message)
                    } else view!!.showMessage(context.getString(R.string.can_not_search_user))
                }
                view!!.dismissLoading()
            }

            override fun onFailure(call: Call<ArrayList<Profile>>, t: Throwable) {
                view!!.showMessage(t.toString())
                view!!.dismissLoading()
            }
        })
    }

}