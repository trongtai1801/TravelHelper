package dut.t2.travelhepler.ui.profile.photos

import dut.t2.travelhelper.base.BaseView
import dut.t2.travelhepler.service.model.Photo

interface PhotosContract {

    interface PhotosViews : BaseView {
        fun getPhotosResult(photos: ArrayList<Photo>)

        fun deletePhotoResult()
    }

    interface PhotoPresenter {
        fun getPhotos()

        fun deletePhoto(id: Int)
    }
}