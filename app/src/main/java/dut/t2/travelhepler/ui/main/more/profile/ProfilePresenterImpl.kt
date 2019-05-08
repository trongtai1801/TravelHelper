package dut.t2.travelhepler.ui.main.more.profile

import android.content.Context
import dut.t2.travelhelper.base.BasePresenter
import dut.t2.travelhelper.service.core.ApiClient
import dut.t2.travelhelper.service.model.Profile
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.Home
import dut.t2.travelhepler.utils.Common
import dut.t2.travelhepler.utils.SessionManager
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfilePresenterImpl(context: Context) : BasePresenter<ProfileContract.ProfileView>(context),
    ProfileContract.ProfilePresenter {
    override fun updateAvatar(avatar: MultipartBody.Part) {
        val req = ApiClient.getService()!!.updateAvatar(SessionManager.getAccessToken()!!, avatar)

        req.enqueue(object : Callback<Profile> {
            override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                if (response.isSuccessful) {
                    var profile: Profile? = null
                    if (response.body() != null)
                        profile = response.body() as Profile
                    profile!!.setDefaultValue()
                    view!!.updateAvatarResult(response.body() as Profile)
                } else {
                    var message = Common.getErrorString(response)
                    if (message != "") {
                        view!!.showMessage(message)
                    } else view!!.showMessage(context.getString(R.string.some_thing_went_wrong))
                }
            }

            override fun onFailure(call: Call<Profile>, t: Throwable) {
                view!!.dismissLoading()
                view!!.showToast(t.toString())
            }
        })

    }

    override fun getHomeInfo() {
        val req = ApiClient.getService()!!.getHomeInfo(SessionManager.getAccessToken()!!)

        req.enqueue(object : Callback<ArrayList<Home>> {
            override fun onResponse(call: Call<ArrayList<Home>>, response: Response<ArrayList<Home>>) {
                if (response.isSuccessful) {
                    if (response.body() != null && (response.body() as ArrayList<Home>).size > 0) {
                        var home = (response.body() as ArrayList<Home>).get(0)
                        home.setDefaultValue()
                        view!!.getHomeInfoResult(home)
                    }
                    else view!!.showToast(context.getString(R.string.dont_have_home))
                    view!!.dismissLoading()
                } else {
                    var message = Common.getErrorString(response)
                    if (message != "") {
                        view!!.showMessage(message)
                    } else view!!.showMessage(context.getString(R.string.some_thing_went_wrong))
                }
            }

            override fun onFailure(call: Call<ArrayList<Home>>, t: Throwable) {
                view!!.showMessage(t.toString())
                view!!.dismissLoading()
            }
        })
    }

}