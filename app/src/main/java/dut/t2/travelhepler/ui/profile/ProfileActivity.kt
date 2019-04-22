package dut.t2.travelhepler.ui.profile

import android.support.v7.app.AppCompatActivity
import android.view.View
import dut.t2.travelhepler.R
import dut.t2.travelhepler.utils.RealmDAO
import dut.t2.travelhepler.utils.SessionManager
import kotlinx.android.synthetic.main.actionbar.*
import kotlinx.android.synthetic.main.activity_profile.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_profile)
class ProfileActivity : AppCompatActivity() {

    @AfterViews
    fun afterViews() {
        initToolbar()
    }

    fun initToolbar() {
        setSupportActionBar(toolbar_profile)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        imgv_actionbar_back.visibility = View.VISIBLE
        tv_actionbar_title.text = RealmDAO.getProfileLogin()!!.fullName

    }
}