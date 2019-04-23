package dut.t2.travelhepler.ui.profile

import android.content.Context
import dut.t2.travelhelper.base.BasePresenter
import dut.t2.travelhelper.service.core.ApiClient
import dut.t2.travelhelper.service.model.Profile
import dut.t2.travelhepler.utils.SessionManager
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.AccessController.getContext

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
                    view!!.dismissLoading()
                    view!!.showToast(response.message())
                }
            }

            override fun onFailure(call: Call<Profile>, t: Throwable) {
                view!!.dismissLoading()
                view!!.showToast(t.toString())
            }
        })

    }

}