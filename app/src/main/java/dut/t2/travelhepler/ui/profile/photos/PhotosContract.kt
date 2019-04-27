package dut.t2.travelhepler.ui.profile.photos

import dut.t2.travelhelper.base.BaseView
import dut.t2.travelhepler.service.model.Photo
import okhttp3.MultipartBody

interface PhotosContract {

    interface PhotosViews : BaseView {
        fun getPhotosResult(photos: ArrayList<Photo>)

        fun deletePhotoResult()

        fun updateImageResult(photo: Photo)
    }

    interface PhotoPresenter {
        fun getPhotos(userId: String)

        fun deletePhoto(id: Int)

        fun uploadImage(photo: MultipartBody.Part)
    }
}