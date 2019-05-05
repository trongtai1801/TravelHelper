package dut.t2.travelhepler.ui.hosts.info

import android.content.Context
import dut.t2.travelhelper.base.BasePresenter
import dut.t2.travelhelper.service.core.ApiClient
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.Home
import dut.t2.travelhepler.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HostInfoPresenterImpl(context: Context) : BasePresenter<HostInfoContract.HostInfoView>(context),
    HostInfoContract.HostInfoPresenter {

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
                } else view!!.showMessage(response.message())
                view!!.dismissLoading()
            }

            override fun onFailure(call: Call<ArrayList<Home>>, t: Throwable) {
                view!!.showMessage(t.toString())
                view!!.dismissLoading()
            }
        })
    }
}