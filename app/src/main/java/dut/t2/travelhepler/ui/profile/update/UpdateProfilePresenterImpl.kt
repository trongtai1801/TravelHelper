package dut.t2.travelhepler.ui.profile.update

import android.content.Context
import com.google.gson.JsonObject
import dut.t2.travelhelper.base.BasePresenter
import dut.t2.travelhelper.service.core.ApiClient
import dut.t2.travelhelper.service.model.Profile
import dut.t2.travelhepler.R
import dut.t2.travelhepler.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateProfilePresenterImpl(context: Context) : BasePresenter<UpdateProfileContract.UpdateProfileView>(context),
    UpdateProfileContract.UpdateProfilePresenter {

    override fun updateProfile(jsonObject: JsonObject) {
        val req = ApiClient.getService()!!.updateUserProfile(SessionManager.getAccessToken()!!, jsonObject)

        req.enqueue(object : Callback<Profile> {
            override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                if (response.isSuccessful) {
                    var profile: Profile? = null
                    if (response.body() != null) {
                        profile = response.body() as Profile
                        profile!!.setDefaultValue()
                        view!!.updateProfileResult(response.body() as Profile)
                    } else view!!.showToast(context.getString(R.string.data_null))
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