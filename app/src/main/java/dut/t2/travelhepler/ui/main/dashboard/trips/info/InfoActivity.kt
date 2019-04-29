package dut.t2.travelhepler.ui.main.dashboard.trips.info

import android.os.Bundle
import android.view.View
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.PublicTrip
import dut.t2.travelhepler.utils.CalendarUtils
import dut.t2.travelhepler.utils.Constant
import kotlinx.android.synthetic.main.activity_trip_info.*
import kotlinx.android.synthetic.main.custom_appbar_layout_dark.*
import org.androidannotations.annotations.EActivity
import java.util.*

@EActivity(R.layout.activity_trip_info)
class InfoActivity : BaseActivity<InfoContract.InfoView, InfoPresenterImpl>(), InfoContract.InfoView {
    var mPublicTrip: PublicTrip? = null

    companion object {
        private var mArrival: Calendar? = Calendar.getInstance()
        private var mDeparture: Calendar? = Calendar.getInstance()
    }

    override fun initPresenter() {
        mPresenter = InfoPresenterImpl(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPublicTrip = intent.getParcelableExtra(Constant.PUBLIC_TRIPS)
    }

    override fun afterViews() {
        initToolbar()

        getArrDepDate()

        atcv_destination.setText(mPublicTrip!!.destination)
        atcv_destination.isFocusable = false

        edt_arrival.setText(CalendarUtils.convertCalendarToString(mArrival))
        edt_arrival.isFocusable = false

        edt_departure.setText(CalendarUtils.convertCalendarToString(mDeparture))
        edt_departure.isFocusable = false


        setNumTravelerTextView()
        edt_num_traveler.isFocusable = false

        edt_trip_description.setText(mPublicTrip!!.description)
        edt_trip_description.isFocusable = false
    }

    fun initToolbar() {
        setSupportActionBar(toolbar_appbar_dark)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        tv_title_appbar.visibility = View.VISIBLE
        img_back_appbar.visibility = View.VISIBLE
        tv_title_appbar.text = getString(R.string.trip_info)
        img_back_appbar.setOnClickListener {
            clearData()
            finish()
        }
    }

    fun setNumTravelerTextView() {
        var numTravelerInt = mPublicTrip!!.travelerNumber
        if (numTravelerInt <= 1) edt_num_traveler.setText(numTravelerInt.toString() + " " + getString(R.string.traveler))
        else edt_num_traveler.setText(numTravelerInt.toString() + " " + getString(R.string.travelers))
    }

    fun getArrDepDate() {
        mArrival = CalendarUtils.convertStringToCalendar(mPublicTrip!!.splitArrivalDate())
        mDeparture = CalendarUtils.convertStringToCalendar(mPublicTrip!!.splitDepartureDate())
    }

    fun clearData() {
        mArrival = Calendar.getInstance()
        mDeparture = Calendar.getInstance()
    }
}