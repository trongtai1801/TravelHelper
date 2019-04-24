package dut.t2.travelhepler.ui.profile.photos

import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.Photo
import dut.t2.travelhepler.utils.Constant
import dut.t2.travelhepler.utils.SessionManager
import kotlinx.android.synthetic.main.activity_photo.*
import kotlinx.android.synthetic.main.custom_appbar_layout.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_photo)
class PhotoActivity : AppCompatActivity() {

    private lateinit var photo: Photo

    @AfterViews
    fun afterView() {
        initToolbar()
        photo = intent.getParcelableExtra(Constant.PHOTO)

        Glide.with(this).load(photo.link)
            .placeholder(this.getDrawable(R.drawable.ic_photo_white))
            .override(300, 200)
            .centerCrop()
            .into(image)
    }

    fun initToolbar() {
        setSupportActionBar(toolbar_appbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        toolbar_appbar.setNavigationOnClickListener { view -> onBackPressed() }
    }
}