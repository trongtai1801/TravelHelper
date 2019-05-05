package dut.t2.travelhepler.ui.travelers.info

import android.content.Context
import com.google.gson.JsonObject
import dut.t2.travelhelper.base.BasePresenter
import dut.t2.travelhelper.service.core.ApiClient
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.CheckFriend
import dut.t2.travelhepler.service.model.Home
import dut.t2.travelhepler.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TravelerInfoPresenterImpl(context: Context) : BasePresenter<TravelerInfoContract.TravelerInfoView>(context),
    TravelerInfoContract.TravelerPresenter {

    override fun getHomeInfo(userId: String) {
        val req = ApiClient.getService()!!.getHomeInfoOfOtherUser(SessionManager.getAccessToken()!!, userId)

        req.enqueue(object : Callback<ArrayList<Home>> {
            override fun onResponse(call: Call<ArrayList<Home>>, response: Response<ArrayList<Home>>) {
                if (response.isSuccessful) {
                    if (response.body() != null && (response.body() as ArrayList<Home>).size > 0) {
                        var home = (response.body() as ArrayList<Home>).get(0)
                        home.setDefaultValue()
                        view!!.getHomeInfoResult(home)
                    } else view!!.showToast(context.getString(R.string.dont_have_home))
                    view!!.dismissLoading()
                }
            }

            override fun onFailure(call: Call<ArrayList<Home>>, t: Throwable) {
                view!!.showMessage(t.toString())
                view!!.dismissLoading()
            }
        })
    }

    override fun checkFriend(userId: String) {
        var req = ApiClient.getService()!!.chekFriend(SessionManager.getAccessToken()!!, userId)

        req.enqueue(object : Callback<CheckFriend> {
            override fun onResponse(call: Call<CheckFriend>, response: Response<CheckFriend>) {
                if (response.isSuccessful) {
                    view!!.checkFriendResult(response.body()!!.isFriend ?: true)
                } else view!!.showToast(context.getString(R.string.can_not_check_friend))
                view!!.dismissLoading()
            }

            override fun onFailure(call: Call<CheckFriend>, t: Throwable) {
                view!!.showMessage(t.toString())
                view!!.dismissLoading()
            }
        })
    }

    override fun addFriend(userId: String) {
        var message = JsonObject()
        message.addProperty("message", "Hello, i want to become your friend")
        var req = ApiClient.getService()!!.addFriend(SessionManager.getAccessToken()!!, userId, message)

        req.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    view!!.addFriendResult()
                } else view!!.showMessage(context.getString(R.string.can_not_add_friend))
                view!!.dismissLoading()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                view!!.showMessage(t.toString())
                view!!.dismissLoading()
            }
        })
    }
}