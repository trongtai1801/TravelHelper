package dut.t2.travelhepler.ui.trips.info

import android.view.MenuItem
import android.view.View
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.PublicTrip
import dut.t2.travelhepler.utils.Constant
import kotlinx.android.synthetic.main.actionbar.*
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_info_trip)
class InfoActivity : BaseActivity<InfoContract.InfoView, InfoPresenterImpl>(), InfoContract.InfoView {
    var publicTrip: PublicTrip? = null

    override fun initPresenter() {
        mPresenter = InfoPresenterImpl(this)
    }

    override fun afterViews() {
        tv_actionbar_title.text = getString(R.string.trip_info)
        imgv_actionbar_back.setOnClickListener { finish() }
        publicTrip = intent.getParcelableExtra(Constant.PUBLIC_TRIPS)
    }
}