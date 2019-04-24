package dut.t2.travelhepler.ui.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.view.View
import com.bumptech.glide.Glide
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhelper.service.model.Profile
import dut.t2.travelhepler.R
import dut.t2.travelhepler.ui.profile.photos.PhotosActivity_
import dut.t2.travelhepler.ui.profile.update.UpdateProfileActivity_
import dut.t2.travelhepler.utils.*
import kotlinx.android.synthetic.main.activity_profile.*
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EActivity
import okhttp3.RequestBody
import okhttp3.MultipartBody
import okhttp3.MediaType
import java.io.File


@EActivity(R.layout.activity_profile)
class ProfileActivity : BaseActivity<ProfileContract.ProfileView, ProfilePresenterImpl>(), ProfileContract.ProfileView {

    override fun initPresenter() {
        mPresenter = ProfilePresenterImpl(this)
    }

    override fun afterViews() {
        initToolbar()
        setupViews()
    }

    @Click(R.id.fab_avatar, R.id.fab_edit_profile, R.id.tv_lb_photos_profile)
    fun onClick(v: View) {
        when (v.id) {
            R.id.fab_avatar -> {
                if (Permission.checkPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE))
                    startPickerImage()
                else
                    Permission.initPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            R.id.fab_edit_profile -> {
                UpdateProfileActivity_.intent(this).startForResult(Constant.REQUEST_CODE_UPDATE_USER_PROFILE)
            }
            R.id.tv_lb_photos_profile -> {
                PhotosActivity_.intent(this).start()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for (grant in grantResults) {
            if (grant == PackageManager.PERMISSION_GRANTED) {
                startPickerImage()
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
                Constant.REQUEST_CODE_UPDATE_USER_PROFILE -> {
                    initToolbar()
                    setupViews()
                    setResult(Activity.RESULT_OK)
                }
            }
        }
    }


    override fun updateAvatarResult(profile: Profile) {
        SessionManager.Profile = profile
        RealmDAO.setProfileLogin(profile)
        Glide.with(this).load(profile!!.avatar)
            .placeholder(this.getDrawable(R.drawable.ic_user_circle))
            .into(img_avatar_toolbar)
        setResult(Activity.RESULT_OK)
        dismissLoading()
    }

    fun initToolbar() {
        setSupportActionBar(toolbar_show_profile)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        title = RealmDAO.getProfileLogin()!!.fullName
        var tmp = title
        Glide.with(this).load(RealmDAO.getProfileLogin()!!.avatar)
            .placeholder(this.getDrawable(R.drawable.ic_avatar_default))
            .into(img_avatar_toolbar)
        toolbar_show_profile.setNavigationOnClickListener { view -> onBackPressed() }
    }

    fun setupViews() {
        tv_content_address_profile.text = SessionManager.Profile?.address
        if (!SessionManager.Profile?.splitBirthday()!!.equals(""))
            tv_content_birthday_profile.text =
                CalendarUtils.convertStringFormat(SessionManager.Profile?.splitBirthday()!!)
        else tv_content_birthday_profile.text = ""
        if (SessionManager.Profile!!.gender) {
            tv_content_gender_profile.text = getString(R.string.male)
        } else
            tv_content_gender_profile.text = getString(R.string.female)
        tv_content_occupation_profile.text = SessionManager.Profile?.address
        tv_content_fluence_profile.text = SessionManager.Profile?.fluentLanguage
        tv_content_learning_profile.text = SessionManager.Profile?.learningLanguage
        tv_content_about_me_profile.text = SessionManager.Profile?.about
        tv_content_interest_profile.text = SessionManager.Profile?.interest
    }

    fun startPickerImage() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, Constant.REQUEST_CODE_PICK_IMAGE)
    }

    fun updateAvatar(data: Intent?) {
        if (data != null) {
            val selectedImageUri = data!!.data
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = contentResolver.query(selectedImageUri, filePathColumn, null, null, null) ?: return

            cursor.moveToFirst()

            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            val filePath = cursor.getString(columnIndex)
            val file = File(filePath)

            val requestBody =
                RequestBody.create(MediaType.parse(contentResolver.getType(selectedImageUri)), file)
            val avatar = MultipartBody.Part.createFormData("file", file.getName(), requestBody)
            showLoading()
            mPresenter!!.updateAvatar(avatar)
        }
    }
}