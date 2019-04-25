package dut.t2.travelhepler.ui.hosts

import android.content.Context
import dut.t2.travelhelper.base.BasePresenter
import dut.t2.travelhelper.service.core.ApiClient
import dut.t2.travelhelper.service.model.Profile
import dut.t2.travelhepler.R
import dut.t2.travelhepler.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HostsPresenterImpl(context: Context) : BasePresenter<HostsContrct.HostsView>(context),
    HostsContrct.HostsPresenter {

    override fun getHosts(address: String) {
        val req = ApiClient.getService()!!.getHosts(SessionManager.getAccessToken()!!, address)

        req.enqueue(object : Callback<ArrayList<Profile>> {
            override fun onResponse(call: Call<ArrayList<Profile>>, response: Response<ArrayList<Profile>>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        if ((response.body() as ArrayList<Profile>).size > 0) {
                            var result: ArrayList<Profile> = response.body() as ArrayList<Profile>
                            for (profile in result) {
                                profile.setDefaultValue()
                            }
                            view!!.getHostsResult(result)
                        } else view!!.showToast(context.getString(R.string.no_host))
                    } else view!!.showMessage(context.getString(R.string.data_null))
                    view!!.dismissLoading()
                }
            }

            override fun onFailure(call: Call<ArrayList<Profile>>, t: Throwable) {
                view!!.showMessage(t.toString())
                view!!.dismissLoading()
            }
        })
    }
}