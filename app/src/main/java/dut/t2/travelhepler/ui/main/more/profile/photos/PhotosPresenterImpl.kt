package dut.t2.travelhepler.ui.main.more.profile.photos

import android.content.Context
import dut.t2.travelhelper.base.BasePresenter
import dut.t2.travelhelper.service.core.ApiClient
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.Photo
import dut.t2.travelhepler.utils.Constant
import dut.t2.travelhepler.utils.SessionManager
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotosPresenterImpl(context: Context) : BasePresenter<PhotosContract.PhotosViews>(context),
    PhotosContract.PhotoPresenter {
    override fun getPhotos(userId: String) {
        val req = ApiClient.getService()!!.getPhotos(SessionManager.getAccessToken()!!, userId)

        req.enqueue(object : Callback<ArrayList<Photo>> {
            override fun onResponse(call: Call<ArrayList<Photo>>, response: Response<ArrayList<Photo>>) {
                if (response.isSuccessful) {
                    if (response.body() != null)
                        view!!.getPhotosResult(response.body() as ArrayList<Photo>)
                    else view!!.showMessage(context.getString(R.string.data_null))
                } else view!!.showMessage(response.message())
                view!!.dismissLoading()
            }

            override fun onFailure(call: Call<ArrayList<Photo>>, t: Throwable) {
                view!!.dismissLoading()
                view!!.showToast(t.toString())
            }
        })
    }

    override fun deletePhoto(id: Int) {
        val req = ApiClient.getService()!!.deletePhoto(SessionManager.getAccessToken()!!, id)

        req.enqueue(object : Callback<Photo> {
            override fun onResponse(call: Call<Photo>, response: Response<Photo>) {
                if (response.code() == Constant.REQUEST_DELETE_SUCCESS) view!!.deletePhotoResult()
                else view!!.showMessage(response.message())
                view!!.dismissLoading()
            }

            override fun onFailure(call: Call<Photo>, t: Throwable) {
                view!!.showToast(t.toString())
                view!!.dismissLoading()
            }
        })
    }

    override fun uploadImage(photo: MultipartBody.Part) {
        val req = ApiClient.getService()!!.uploadImage(SessionManager.getAccessToken()!!, photo)

        req.enqueue(object : Callback<Photo> {
            override fun onResponse(call: Call<Photo>, response: Response<Photo>) {
                if (response.isSuccessful) {
                    if (response.body() != null)
                        view!!.updateImageResult(response.body() as Photo)
                    else view!!.showMessage(context.getString(R.string.response_body_null))
                    view!!.dismissLoading()
                } else {
                    view!!.dismissLoading()
                    view!!.showToast(response.message())
                }
            }

            override fun onFailure(call: Call<Photo>, t: Throwable) {
                view!!.dismissLoading()
                view!!.showToast(t.toString())
            }
        })
    }
}