package dut.t2.travelhepler.ui.main.more.profile.photos

import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.Photo
import dut.t2.travelhepler.utils.Constant
import kotlinx.android.synthetic.main.activity_photo.*
import kotlinx.android.synthetic.main.custom_appbar_layout_dark.*
import kotlinx.android.synthetic.main.custom_appbar_layout_dark.img_back_appbar
import kotlinx.android.synthetic.main.custom_appbar_layout_dark.tv_title_appbar
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
        setSupportActionBar(toolbar_appbar_dark)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        tv_title_appbar.visibility = View.GONE
        img_back_appbar.visibility = View.VISIBLE
        img_back_appbar.setOnClickListener { onBackPressed() }
    }
}