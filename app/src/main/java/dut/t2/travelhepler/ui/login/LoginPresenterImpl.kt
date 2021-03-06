package dut.t2.travelhepler.ui.login

import android.content.Context
import dut.t2.travelhelper.base.BasePresenter
import dut.t2.travelhelper.service.core.ApiClient
import dut.t2.travelhelper.service.model.User
import dut.t2.travelhelper.service.response.LoginResponse
import dut.t2.travelhepler.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenterImpl(context: Context) : BasePresenter<LoginContract.LoginView>(context),
    LoginContract.LoginPresenter {
    override fun login(user: User) {
        var req: Call<LoginResponse> = ApiClient.getService()!!.login(user)
        req.enqueue(object : Callback<LoginResponse> {

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response != null) {
                    SessionManager.setAccessToken(response.body()!!.token)

                    var profile = response.body()!!.profile
                    //realm is not allow nul field
                    profile.setDefaultValue()
                    SessionManager.saveProfile(profile)

                    view!!.loginResult()
                } else {
                    view!!.showToast(response.message())
                    view!!.dismissLoading()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                view!!.dismissLoading()
                view!!.showMessage(t.toString())
            }
        })
    }

}