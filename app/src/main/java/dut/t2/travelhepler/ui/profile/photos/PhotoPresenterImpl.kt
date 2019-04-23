package dut.t2.travelhepler.ui.profile.photos

import android.content.Context
import dut.t2.travelhelper.base.BasePresenter
import dut.t2.travelhelper.service.core.ApiClient
import dut.t2.travelhepler.service.model.Photo
import dut.t2.travelhepler.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotoPresenterImpl(context: Context) : BasePresenter<PhotosContract.PhotosViews>(context),
    PhotosContract.PhotoPresenter {
    override fun getPhotos() {
        val req = ApiClient.getService()!!.getPhotos(SessionManager.getAccessToken()!!)

        req.enqueue(object : Callback<ArrayList<Photo>> {
            override fun onResponse(call: Call<ArrayList<Photo>>, response: Response<ArrayList<Photo>>) {
                if (response.isSuccessful) {
                    if (response.body() != null)
                        view!!.getPhotosResult(response.body() as ArrayList<Photo>)
                } else {
                    view!!.dismissLoading()
                    view!!.showToast(response.message())
                }
            }

            override fun onFailure(call: Call<ArrayList<Photo>>, t: Throwable) {
                view!!.dismissLoading()
                view!!.showToast(t.toString())
            }
        })
    }
}