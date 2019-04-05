package dut.t2.travelhelper.ui.signup

import com.google.gson.JsonObject
import dut.t2.travelhelper.base.BasePresenter
import dut.t2.travelhelper.service.core.ApiClient
import dut.t2.travelhepler.service.response.SignUpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpPresenterImpl : BasePresenter<SignUpContract.SignUpView>(), SignUpContract.SignUpPresenter  {
    override fun signUp(jsonObject: JsonObject) {
        var req: Call<SignUpResponse> = ApiClient.getService()!!.signUp(jsonObject);
        req.enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                if (response.body()!!.succeeded) view!!.signUpResult()
                else view!!.showMessage(response.body()!!.errors.get(0).description)
                view!!.dismissLoading()
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                view!!.showMessage(t.toString())
                view!!.dismissLoading()
            }
        })
    }
}