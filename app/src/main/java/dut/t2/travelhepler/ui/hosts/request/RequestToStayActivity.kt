package dut.t2.travelhepler.ui.hosts.request

import android.view.View
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhepler.R
import kotlinx.android.synthetic.main.custom_appbar_layout_dark.*
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_request_to_stay)
class RequestToStayActivity : BaseActivity<RequestToStayContract.RequestToStayView, RequestToStayPresenterImpl>(),
    RequestToStayContract.RequestToStayView {
    override fun initPresenter() {
        mPresenter = RequestToStayPresenterImpl(this)
    }

    override fun afterViews() {
        initToolbar()
    }

    fun initToolbar() {
        setSupportActionBar(toolbar_appbar_dark)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        tv_title_appbar.visibility = View.VISIBLE
        img_back_appbar.visibility = View.VISIBLE
        tv_title_appbar.text = getString(R.string.request_to_stay)
        img_back_appbar.setOnClickListener { onBackPressed() }
    }
}