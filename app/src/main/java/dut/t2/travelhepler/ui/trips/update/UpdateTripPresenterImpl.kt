package dut.t2.travelhepler.ui.trips.update

import android.content.Context
import com.google.gson.JsonObject
import dut.t2.travelhelper.base.BasePresenter
import dut.t2.travelhelper.service.core.ApiClient
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.PublicTrip
import dut.t2.travelhepler.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateTripPresenterImpl(context: Context) : BasePresenter<UpdateTripContract.UpdateTripView>(context),
    UpdateTripContract.UpdateViewPresenter {

    /**
     * function to get all address exist in database to suggest for user
     *
     * */

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

    /**
     * function to update a public trip
     *
     * @param id the id of the public trip that need to update
     * @param trip is the body of the request that contain option parameter
     * (Destination, ArrivalDate(Option), DepartureDate, TravelerNumber, Description)
     *
     * */

    override fun updatePublicTrip(id: Int, trip: JsonObject) {
        var req = ApiClient.getService()!!.updatePublicTrip(SessionManager.getAccessToken()!!, id, trip)

        req.enqueue(object : Callback<PublicTrip> {
            override fun onResponse(call: Call<PublicTrip>, response: Response<PublicTrip>) {
                if (response.isSuccessful && (response.body() as PublicTrip) != null)
                    view!!.updatePublicTripResult(response.body() as PublicTrip)
                else view!!.showToast(response.message())
                view!!.dismissLoading()
            }

            override fun onFailure(call: Call<PublicTrip>, t: Throwable) {
                view!!.showMessage(t.toString())
                view!!.dismissLoading()
            }
        })
    }
}