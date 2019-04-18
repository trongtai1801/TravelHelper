package dut.t2.travelhepler.ui.trips.create

import android.view.View
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhepler.R
import kotlinx.android.synthetic.main.actionbar.*
import kotlinx.android.synthetic.main.activity_trip_info.*
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_trip_info)
class CreateTripActivity : BaseActivity<CreateTripContract.CreateTripView, CreateTripPresenterImpl>(),
    CreateTripContract.CreateTripView {
    override fun initPresenter() {
        mPresenter = CreateTripPresenterImpl(this)
    }

    override fun afterViews() {
        tv_actionbar_title.text = getString(R.string.create_trip)
        setViews()
    }

    fun setViews() {
        img_add_num_traveler.visibility = View.VISIBLE
        img_sub_num_traveler.visibility = View.VISIBLE
        edt_arr_dep.isFocusable = false
        edt_num_traveler.isFocusable = false
        edt_num_traveler.setText("0 " + getString(R.string.traveler))
    }
}