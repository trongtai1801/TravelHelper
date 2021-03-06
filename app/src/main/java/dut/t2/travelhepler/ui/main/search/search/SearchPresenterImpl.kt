package dut.t2.travelhepler.ui.main.search.search

import android.content.Context
import dut.t2.travelhelper.base.BasePresenter
import dut.t2.travelhelper.service.core.ApiClient
import dut.t2.travelhepler.R
import dut.t2.travelhepler.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchPresenterImpl(context: Context) : BasePresenter<SearchContract.SearchView>(context),
    SearchContract.SearchPresenter {
    override fun getSuggestAddress() {
        var req = ApiClient.getService()!!.getSuggestAddress(SessionManager.getAccessToken()!!)

        req.enqueue(object : Callback<ArrayList<String>> {
            override fun onResponse(call: Call<ArrayList<String>>, response: Response<ArrayList<String>>) {
                if (response.isSuccessful) {
                    if (response.body() != null && (response.body() as ArrayList<String>).size > 0) {
                        view!!.getSuggestAddressResult(response.body() as ArrayList<String>)
                    } else {
                        view!!.showMessage(context.getString(R.string.addresses_empty))
                    }
                } else {
                    view!!.showMessage(response.message())
                }
                view!!.dismissLoading()
            }

            override fun onFailure(call: Call<ArrayList<String>>, t: Throwable) {
                view!!.showMessage(t.toString())
                view!!.dismissLoading()
            }
        })
    }
}