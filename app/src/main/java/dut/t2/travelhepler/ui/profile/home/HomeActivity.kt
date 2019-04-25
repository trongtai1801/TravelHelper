package dut.t2.travelhepler.ui.profile.home

import android.view.View
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhepler.R
import kotlinx.android.synthetic.main.custom_appbar_layout_dark.*
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_home)
class HomeActivityity : BaseActivity<HomeContract.HomeView, HomePresenterImpl>(), HomeContract.HomeView {
    override fun initPresenter() {
        mPresenter = HomePresenterImpl(this)
    }

    override fun afterViews() {
        initToolbar()
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