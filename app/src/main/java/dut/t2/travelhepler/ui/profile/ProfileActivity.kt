package dut.t2.travelhepler.ui.profile

import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import dut.t2.travelhepler.R
import dut.t2.travelhepler.utils.RealmDAO
import kotlinx.android.synthetic.main.activity_profile.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_profile)
class ProfileActivity : AppCompatActivity() {

    @AfterViews
    fun afterViews() {
        initToolbar()
    }

    fun initToolbar() {
        setSupportActionBar(toolbar_profile)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        title = RealmDAO.getProfileLogin()!!.fullName
        Glide.with(this).load(RealmDAO.getProfileLogin()!!.avatar)
            .placeholder(this.getDrawable(R.drawable.profile_cover))
            .into(img_avatar_toolbar)
        toolbar_profile.setNavigationOnClickListener { view -> onBackPressed() }
    }

    @Click(R.id.fab_avatar, R.id.fab_edit_profile)
    fun onClick(v: View) {
        when (v.id) {
            R.id.fab_avatar -> {
                Toast.makeText(this, "Update avatar", Toast.LENGTH_LONG).show()
            }
            R.id.fab_edit_profile -> {
                Toast.makeText(this, "Edit profile", Toast.LENGTH_LONG).show()
            }
        }
    }
}