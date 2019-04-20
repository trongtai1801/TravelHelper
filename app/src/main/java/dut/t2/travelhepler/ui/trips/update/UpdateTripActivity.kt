package dut.t2.travelhepler.ui.trips.update

import android.os.Bundle
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.PublicTrip
import dut.t2.travelhepler.utils.Constant
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_trip_info)
class UpdateTripActivity : BaseActivity<UpdateTripContract.UpdateTripView, UpdateTripPresenterImpl>(),
    UpdateTripContract.UpdateTripView {

    private var mPublicTrip: PublicTrip? = null

    override fun initPresenter() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPublicTrip = intent.getParcelableExtra(Constant.PUBLIC_TRIPS)
    }

    override fun afterViews() {
        if (mPublicTrip != null)
            showToast(mPublicTrip!!.destination)
    }
}