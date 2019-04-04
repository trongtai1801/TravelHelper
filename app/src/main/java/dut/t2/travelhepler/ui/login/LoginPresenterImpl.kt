package dut.t2.travelhepler.ui.login

import dut.t2.basemvp.base.BasePresenter
import dut.t2.travelhelper.service.core.ApiClient
import dut.t2.travelhelper.service.model.User
import dut.t2.travelhelper.service.response.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenterImpl : BasePresenter<LoginContract.LoginView>(), LoginContract.LoginPresenter {
    override fun login(user: User) {
        var req: Call<LoginResponse> = ApiClient.getService()!!.login(user)
        req.enqueue(object : Callback<LoginResponse> {

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) view!!.loginResult()
                else {
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