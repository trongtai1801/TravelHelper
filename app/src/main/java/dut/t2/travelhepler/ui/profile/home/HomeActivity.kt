package dut.t2.travelhepler.ui.profile.home

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.Home
import dut.t2.travelhepler.utils.Constant
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.custom_appbar_layout_dark.*
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_home)
class HomeActivity : BaseActivity<HomeContract.HomeView, HomePresenterImpl>(), HomeContract.HomeView {
    private var mHome: Home? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHome = intent.getParcelableExtra(Constant.HOME)
    }
    override fun initPresenter() {
        mPresenter = HomePresenterImpl(this)
    }

    override fun afterViews() {
        initToolbar()
        setupViews()
    }

    fun initToolbar() {
        setSupportActionBar(toolbar_appbar_dark)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        tv_title_appbar.visibility = View.GONE
        img_back_appbar.visibility = View.VISIBLE
        img_back_appbar.setOnClickListener { onBackPressed() }
    }

    fun setupViews() {
        if (mHome != null) {
            edt_max_guest_home.setText(mHome!!.id.toString())
            edt_preferred_gender_home.setText(mHome!!.preferedGender)
            edt_sleeping_arrangements_home.setText(mHome!!.sleepingArrangement)
            edt_description_sleeping_arrangement_home.setText(mHome!!.sleepingDescription)
            edt_description_sleeping_arrangement_home.movementMethod = ScrollingMovementMethod()
            edt_transportation_access_home.setText(mHome!!.transportationAccess)
            edt_allow_thing_home.setText(mHome!!.allowedThing)
            edt_allow_thing_home.movementMethod = ScrollingMovementMethod()
            edt_stuff_home.setText(mHome!!.stuff)
            edt_stuff_home.movementMethod = ScrollingMovementMethod()
            edt_add_info_home.setText(mHome!!.additionInfo)
        }
    }
}