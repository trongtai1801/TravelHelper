package dut.t2.travelhepler.ui.main.more.profile.photos

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.GridLayoutManager
import android.view.View
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.Photo
import dut.t2.travelhepler.utils.Constant
import dut.t2.travelhepler.utils.Permission
import dut.t2.travelhepler.utils.RealmDAO
import kotlinx.android.synthetic.main.activity_photos.*
import kotlinx.android.synthetic.main.custom_appbar_layout_dark.*
import kotlinx.android.synthetic.main.custom_appbar_layout_dark.img_back_appbar
import kotlinx.android.synthetic.main.custom_appbar_layout_dark.tv_title_appbar
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EActivity
import java.io.File
import android.support.design.widget.CoordinatorLayout


@SuppressLint("Registered")
@EActivity(R.layout.activity_photos)
class PhotosActivity : BaseActivity<PhotosContract.PhotosViews, PhotosPresenterImpl>(), PhotosContract.PhotosViews {

    private var mPhotos = ArrayList<Photo>()
    private lateinit var mAdapter: PhotosAdapter

    companion object {
        var mUserId: String = ""
    }

    override fun initPresenter() {
        mPresenter = PhotosPresenterImpl(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mUserId = this.intent.getStringExtra(Constant.USER_ID)
    }

    override fun afterViews() {
        initToolbar()
        initRcv()
        if (!mUserId.equals(RealmDAO.getProfileLogin()!!.id)) fab_add_photo.hide()
        else fab_add_photo.show()
        swf_photos.setOnRefreshListener {
            mPresenter!!.getPhotos(mUserId)
        }
        showLoading()
        mPresenter!!.getPhotos(mUserId)
    }

    @Click(R.id.fab_add_photo)
    fun onClick(v: View) {
        when (v.id) {
            R.id.fab_add_photo -> {
                if (Permission.checkPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (mUserId == RealmDAO.getProfileLogin()!!.id)
                        startPickerImage()
                } else
                    Permission.initPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                Constant.REQUEST_CODE_PICK_IMAGE -> {
                    updateAvatar(data)
                }
            }
        }
    }

    override fun getPhotosResult(photos: ArrayList<Photo>) {
        dismissSwipeRefreshLayout()
        if (photos.size > 0) {
            mPhotos.clear()
            mPhotos.addAll(photos)
            mAdapter.notifyDataSetChanged()
        } else showToast(getString(R.string.no_photo))
        dismissLoading()
    }

    override fun deletePhotoResult() {
        showLoading()
        mPresenter!!.getPhotos(mUserId)
    }

    override fun updateImageResult(photo: Photo) {
        mPhotos.add(0, photo)
        mAdapter.notifyDataSetChanged()
        dismissLoading()
    }

    fun initToolbar() {
        setSupportActionBar(toolbar_appbar_dark)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        tv_title_appbar.visibility = View.VISIBLE
        tv_title_appbar.text = getString(R.string.photos)
        img_back_appbar.visibility = View.VISIBLE
        img_back_appbar.setOnClickListener { onBackPressed() }
    }

    fun initRcv() {
        this.mPhotos.clear()
        rcv_photos.setHasFixedSize(true)
        mAdapter = PhotosAdapter(this, mPhotos, object : PhotosAdapter.ItemClickListener {
            override fun onClick(photo: Photo) {
                PhotoActivity_.intent(this@PhotosActivity).extra(Constant.PHOTO, photo).start()
            }

            override fun onPopupItemClick(itemId: Int, photo: Photo) {
                when (itemId) {
                    R.id.item_delete -> {
                        showLoading()
                        mPresenter!!.deletePhoto(photo.id)
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

    fun startPickerImage() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, Constant.REQUEST_CODE_PICK_IMAGE)
    }

    @SuppressLint("Recycle")
    fun updateAvatar(data: Intent?) {
        if (data != null) {
            val selectedImageUri = data.data
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = contentResolver.query(selectedImageUri, filePathColumn, null, null, null) ?: return

            cursor.moveToFirst()

            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            val filePath = cursor.getString(columnIndex)
            val file = File(filePath)

            val requestBody =
                RequestBody.create(MediaType.parse(contentResolver.getType(selectedImageUri)), file)
            val photo = MultipartBody.Part.createFormData("file", file.getName(), requestBody)
            showLoading()
            mPresenter!!.uploadImage(photo)
        }
    }

    fun hideShowFab(fab: FloatingActionButton) {
        val p = fab.layoutParams as CoordinatorLayout.LayoutParams
        p.anchorId = View.NO_ID
        fab.layoutParams = p
        fab.hide()
    }
}