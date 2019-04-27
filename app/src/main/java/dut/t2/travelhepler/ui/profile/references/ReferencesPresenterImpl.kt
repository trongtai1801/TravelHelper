package dut.t2.travelhepler.ui.profile.references

import android.content.Context
import dut.t2.travelhelper.base.BasePresenter
import dut.t2.travelhelper.service.core.ApiClient
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.Reference
import dut.t2.travelhepler.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReferencesPresenterImpl(context: Context) : BasePresenter<ReferencesContract.ReferencesView>(context),
    ReferencesContract.ReferencesPresenter {

    override fun getReferences(userId: String) {
        val req = ApiClient.getService()!!.getReferences(userId)

        req.enqueue(object : Callback<ArrayList<Reference>> {
            override fun onResponse(call: Call<ArrayList<Reference>>, response: Response<ArrayList<Reference>>) {
                if (response.isSuccessful) {
                    var result = ArrayList<Reference>()
                    if (response.body() as ArrayList<Reference> != null) {
                        result.clear()
                        result.addAll(response.body() as ArrayList<Reference>)
                        view!!.getReferencesResult(result)
                    } else view!!.showMessage(context.getString(R.string.data_null))
                    view!!.dismissLoading()
                }
            }

            override fun onFailure(call: Call<ArrayList<Reference>>, t: Throwable) {
                view!!.showMessage(t.toString())
                view!!.dismissLoading()
            }
        })
    }
}