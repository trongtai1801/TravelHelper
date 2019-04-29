package dut.t2.travelhepler.ui.hosts.request

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.gson.JsonObject
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhelper.service.model.Profile
import dut.t2.travelhepler.R
import dut.t2.travelhepler.utils.Constant
import kotlinx.android.synthetic.main.activity_request_to_stay.*
import kotlinx.android.synthetic.main.custom_appbar_layout_dark.*
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EActivity
import java.text.SimpleDateFormat
import java.util.*

@EActivity(R.layout.activity_request_to_stay)
class RequestToStayActivity : BaseActivity<RequestToStayContract.RequestToStayView, RequestToStayPresenterImpl>(),
    RequestToStayContract.RequestToStayView {

    var mHost: Profile?? = null

    companion object {
        private var sNumTraveler = 0
        private var mArrival: Calendar? = null
        private var mDeparture: Calendar? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHost = intent.getParcelableExtra(Constant.HOST)
    }

    override fun initPresenter() {
        mPresenter = RequestToStayPresenterImpl(this)
    }

    override fun afterViews() {
        initToolbar()
        setupView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_create, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        clearData()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.item_add -> {
                submit()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @Click(R.id.tv_arrival, R.id.tv_departure, R.id.img_add_num_traveler, R.id.img_sub_num_traveler)
    fun onClick(v: View) {
        when (v.id) {
            R.id.tv_arrival -> {
                showCalendar(tv_arrival)
            }
            R.id.tv_departure -> {
                showCalendar(tv_departure)
            }
            R.id.img_add_num_traveler -> {
                sNumTraveler++
                setNumTravelerTextView()
            }
            R.id.img_sub_num_traveler -> {
                sNumTraveler--
                if (sNumTraveler < 0) sNumTraveler = 0
                setNumTravelerTextView()
            }
        }
    }

    override fun createRequestToStayResult() {
        showToast(getString(R.string.created))
        dismissLoading()
        finish()
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

    fun setupView() {
        Glide.with(this).load(mHost)
            .placeholder(this.getDrawable(R.drawable.ic_user_circle))
            .into(cir_img_host_avatar)
        tv_host_name.text = mHost!!.fullName
        tv_host_address.text = mHost!!.address
    }

    fun showCalendar(v: TextView) {
        val calendar = Calendar.getInstance()

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            when (v.id) {
                R.id.tv_arrival -> {
                    mArrival = calendar
                }
                R.id.tv_departure -> {
                    mDeparture = calendar
                }
            }

            if (isValidDate()) {
                val sdf = SimpleDateFormat(Constant.DATE_FORMAT_SEND, Locale.US)
                v.setText(sdf.format(calendar.time))
            } else showToast(getString(R.string.warning_arrival_must_be_after_departure))
        }

        var dp = DatePickerDialog(this@RequestToStayActivity, dateSetListener, year, month, day)
        dp.datePicker.minDate = calendar.getTimeInMillis()
        dp.show()
    }

    fun isValidDate(): Boolean {
        if (mArrival == null || mDeparture == null) return true
        else return (mArrival!!.before(mDeparture))
        return false
    }

    fun setNumTravelerTextView() {
        if (sNumTraveler <= 1) tv_num_traveler.setText(sNumTraveler.toString() + " " + getString(R.string.traveler))
        else tv_num_traveler.setText(sNumTraveler.toString() + " " + getString(R.string.travelers))
    }

    fun clearData() {
        mArrival = null
        mDeparture = null
        sNumTraveler = 0
    }

    fun submit() {
        var trip = JsonObject()
        if (!invalidField()) {
            trip.addProperty("ArrivalDate", tv_arrival.text.toString())
            trip.addProperty("DepartureDate", tv_departure.text.toString())
            trip.addProperty("TravelerNumber", sNumTraveler)
            trip.addProperty("Message", "")
            showLoading()
            mPresenter!!.createRequestToStay(mHost!!.id, trip)
        }
    }

    fun invalidField(): Boolean {
        if (tv_arrival.text.isEmpty()) {
            showToast(getString(R.string.warning_input_arrival_date))
            return true
        }
        if (tv_departure.text.isEmpty()) {
            showToast(getString(R.string.warning_input_departure_date))
            return true
        }
        return false
    }
}