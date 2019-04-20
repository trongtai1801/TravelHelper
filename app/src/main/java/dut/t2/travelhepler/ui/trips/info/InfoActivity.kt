package dut.t2.travelhepler.ui.trips.info

import android.os.Bundle
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.PublicTrip
import dut.t2.travelhepler.utils.Constant
import kotlinx.android.synthetic.main.actionbar.*
import kotlinx.android.synthetic.main.activity_trip_info.*
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_trip_info)
class InfoActivity : BaseActivity<InfoContract.InfoView, InfoPresenterImpl>(), InfoContract.InfoView {
    var mPublicTrip: PublicTrip? = null

    override fun initPresenter() {
        mPresenter = InfoPresenterImpl(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPublicTrip = intent.getParcelableExtra(Constant.PUBLIC_TRIPS)
    }

    override fun afterViews() {
        tv_actionbar_title.text = getString(R.string.trip_info)
        imgv_actionbar_back.setOnClickListener { finish() }

        atcv_destination.setText(mPublicTrip!!.destination)
        atcv_destination.isFocusable = false

        edt_arrival.setText(mPublicTrip!!.arrivalDate.split("T")[0])
        edt_arrival.isFocusable = false

        edt_departure.setText(mPublicTrip!!.departureDate.split("T")[0])
        edt_departure.isFocusable = false

        var numTravelerInt = mPublicTrip!!.travelerNumber
        var numTravelerStr = ""
        if (numTravelerInt <= 1) numTravelerStr = numTravelerInt.toString() + " " + getString(R.string.traveler)
        else numTravelerStr = numTravelerInt.toString() + " " + getString(R.string.travelers)
        edt_num_traveler.setText(numTravelerStr)
        edt_num_traveler.isFocusable = false

        edt_trip_description.setText(mPublicTrip!!.description)
        edt_trip_description.isFocusable = false
    }
}