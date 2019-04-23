package dut.t2.travelhepler.ui.profile.photos

import android.support.v7.widget.GridLayoutManager
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.Photo
import dut.t2.travelhepler.utils.SessionManager
import kotlinx.android.synthetic.main.activity_photos.*
import kotlinx.android.synthetic.main.custom_appbar_layout.*
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_photos)
class PhotoActivity : BaseActivity<PhotosContract.PhotosViews, PhotoPresenterImpl>(), PhotosContract.PhotosViews {

    private var mPhotos = ArrayList<Photo>()
    private lateinit var mAdapter: PhotosAdapter

    override fun initPresenter() {
        mPresenter = PhotoPresenterImpl(this)
    }

    override fun afterViews() {
        initToolbar()
        initRcv()
        swf_photos.setOnRefreshListener {
            showLoading()
            mPresenter!!.getPhotos()
        }
        showLoading()
        mPresenter!!.getPhotos()
    }

    override fun getPhotosResult(photos: ArrayList<Photo>) {
        dismissSwipeRefreshLayout()
        if (photos != null) {
            if (photos.size > 0) {
                mPhotos.clear()
                mPhotos.addAll(photos)
                mAdapter.notifyDataSetChanged()
            } else getString(R.string.no_photo)
        }
        dismissLoading()
    }

    fun initToolbar() {
        setSupportActionBar(toolbar_appbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        title = SessionManager.Profile!!.fullName
        toolbar_appbar.setNavigationOnClickListener { view -> onBackPressed() }
    }

    fun initRcv() {
        mPhotos.clear()
        rcv_photos.setHasFixedSize(true)
        mAdapter = PhotosAdapter(this, mPhotos, object : PhotosAdapter.ItemClickListener {
            override fun onClick(photo: Photo) {
                showToast(photo.link)
            }

            override fun onPopupItemClick(itemId: Int, photo: Photo) {
                when (itemId) {
                    R.id.item_delete -> {
                        showToast("Delete" + photo.link)
                    }
                }
            }
        })
        rcv_photos.layoutManager = GridLayoutManager(this, 3)
        rcv_photos.adapter = mAdapter
    }

    fun dismissSwipeRefreshLayout() {
        if (swf_photos != null && swf_photos.isRefreshing)
            swf_photos.isRefreshing = false
    }
}