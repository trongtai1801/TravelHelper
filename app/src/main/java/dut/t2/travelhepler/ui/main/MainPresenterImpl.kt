package dut.t2.travelhepler.ui.main

import android.content.Context
import dut.t2.travelhelper.base.BasePresenter
import dut.t2.travelhelper.service.core.ApiClient
import dut.t2.travelhelper.service.model.Profile
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.PublicTrip
import dut.t2.travelhepler.utils.Common
import dut.t2.travelhepler.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenterImpl(context: Context) : BasePresenter<MainContract.MainView>(context), MainContract.MainPresenter {
    override fun getPublicTrips() {
        val req = ApiClient.getService()!!.getPublicTrips(SessionManager.getAccessToken()!!)

        req.enqueue(object : Callback<ArrayList<PublicTrip>> {
            override fun onResponse(call: Call<ArrayList<PublicTrip>>, response: Response<ArrayList<PublicTrip>>) {
                if (response.isSuccessful) {
                    view!!.getPublicTripsResult(response.body()!!)
                } else {
                    view!!.getPublicTripsResult(null)
                    var message = Common.getErrorString(response)
                    if (message != "") {
                        view!!.showMessage(message)
                    } else view!!.showMessage(context.getString(R.string.get_public_trips_error))
                }
                view!!.dismissLoading()
            }

            override fun onFailure(call: Call<ArrayList<PublicTrip>>, t: Throwable) {
                view!!.dismissLoading()
                view!!.getPublicTripsResult(null)
                view!!.showMessage(t.toString())
            }
        })
    }

    override fun deletePublicTrip(id: Int) {
        val req = ApiClient.getService()!!.deletePublicTrip(SessionManager.getAccessToken()!!, id)

        req.enqueue(object : Callback<PublicTrip> {
            override fun onResponse(call: Call<PublicTrip>, response: Response<PublicTrip>) {
                if (response.code() == 204) {
                    view!!.deletePublicTripResult()
                } else {
                    var message = Common.getErrorString(response)
                    if (message != "") {
                        view!!.showMessage(message)
                    } else view!!.showMessage(context.getString(R.string.can_not_delete_trip))
                }
                view!!.dismissLoading()
            }

            override fun onFailure(call: Call<PublicTrip>, t: Throwable) {
                view!!.dismissLoading()
                view!!.showMessage(t.toString())
            }
        })
    }

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
                } else {
                    var message = Common.getErrorString(response)
                    if (message != "") {
                        view!!.showMessage(message)
                    } else view!!.showMessage(context.getString(R.string.some_thing_went_wrong))
                }
                view!!.dismissLoading()
            }

            override fun onFailure(call: Call<ArrayList<Profile>>, t: Throwable) {
                view!!.showMessage(t.toString())
                view!!.dismissLoading()
            }
        })
    }

    override fun getTravelers(address: String) {
        val req = ApiClient.getService()!!.getTravelers(SessionManager.getAccessToken()!!, address)

        req.enqueue(object : Callback<ArrayList<PublicTrip>> {
            override fun onResponse(call: Call<ArrayList<PublicTrip>>, response: Response<ArrayList<PublicTrip>>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        if ((response.body() as ArrayList<PublicTrip>).size > 0) {
                            var result: ArrayList<PublicTrip> = response.body() as ArrayList<PublicTrip>
                            for (publictrip: PublicTrip in result) {
                                publictrip.user!!.setDefaultValue()
                            }
                            view!!.getTravelersResult(result)
                        } else view!!.showToast(context.getString(R.string.no_travelers))
                    } else view!!.showMessage(context.getString(R.string.data_null))
                } else {
                    var message = Common.getErrorString(response)
                    if (message != "") {
                        view!!.showMessage(message)
                    } else view!!.showMessage(context.getString(R.string.some_thing_went_wrong))
                }
                view!!.dismissLoading()
            }

            override fun onFailure(call: Call<ArrayList<PublicTrip>>, t: Throwable) {
                view!!.showMessage(t.toString())
                view!!.dismissLoading()
            }
        })
    }
}