package dut.t2.travelhelper.ui.signup

import android.content.Context
import com.google.gson.JsonObject
import dut.t2.travelhelper.base.BasePresenter
import dut.t2.travelhelper.service.core.ApiClient
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.response.SignUpResponse
import dut.t2.travelhepler.utils.Common
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpPresenterImpl(context: Context) : BasePresenter<SignUpContract.SignUpView>(context),
    SignUpContract.SignUpPresenter {
    override fun signUp(jsonObject: JsonObject) {
        var req: Call<SignUpResponse> = ApiClient.getService()!!.signUp(jsonObject);
        req.enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                if (response.body()!!.succeeded) view!!.signUpResult()
                else {
                    var message = Common.getErrorString(response)
                    if (message != "") {
                        view!!.showMessage(message)
                    } else view!!.showMessage(context.getString(R.string.some_thing_went_wrong))
                }
                view!!.dismissLoading()
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                view!!.showMessage(t.toString())
                view!!.dismissLoading()
            }
        })
    }
}