package dut.t2.travelhepler.ui.main.more.profile.references

import android.content.Context
import com.google.gson.JsonObject
import dut.t2.travelhelper.base.BasePresenter
import dut.t2.travelhelper.service.core.ApiClient
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.Reference
import dut.t2.travelhepler.utils.Common
import dut.t2.travelhepler.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WriteReferencePresenterImpl(context: Context) : BasePresenter<WriteReferenceContract.WriteReferenceView>(context),
    WriteReferenceContract.WtireReferencePresenter {

    override fun writeReference(userId: String, reference: JsonObject) {
        var req = ApiClient.getService()!!.writeReference(SessionManager.getAccessToken()!!, userId, reference)

        req.enqueue(object : Callback<Reference> {
            override fun onResponse(call: Call<Reference>, response: Response<Reference>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        var result = response.body() as Reference
                        result.sender.setDefaultValue()
                        view!!.writeReferenceResult(result)
                    } else view!!.showMessage(context.getString(R.string.data_null))
                } else {
                    var message = Common.getErrorString(response)
                    if (message != "") {
                        view!!.showMessage(message)
                    } else view!!.showMessage(context.getString(R.string.can_not_create_reference))
                }
                view!!.dismissLoading()
            }

            override fun onFailure(call: Call<Reference>, t: Throwable) {
                view!!.showMessage(t.toString())
                view!!.dismissLoading()
            }
        })
    }
}