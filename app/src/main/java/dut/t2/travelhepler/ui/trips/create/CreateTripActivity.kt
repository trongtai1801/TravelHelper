package dut.t2.travelhepler.ui.trips.create

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import com.google.gson.JsonObject
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.PublicTrip
import dut.t2.travelhepler.utils.Constant
import kotlinx.android.synthetic.main.custom_toolbar.*
import kotlinx.android.synthetic.main.activity_trip_info.*
import org.androidannotations.annotations.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@EActivity(R.layout.activity_trip_info)
class CreateTripActivity : BaseActivity<CreateTripContract.CreateTripView, CreateTripPresenterImpl>(),
    CreateTripContract.CreateTripView {

    private var mDestinations = ArrayList<String>()
    private var mDestinationAdapter: ArrayAdapter<String>? = null
    val numCharToSuggest = 3

    companion object {
        private var sNumTraveler = 1
        private var mArrival: Calendar? = null
        private var mDeparture: Calendar? = null
    }

    override fun initPresenter() {
        mPresenter = CreateTripPresenterImpl(this)
    }

    override fun afterViews() {
        initToolbar()
        setViews()
        showLoading()
        mPresenter!!.getSuggestAddress()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_create, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.item_add -> {
                submit()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @TextChange(R.id.atcv_destination)
    fun onTextChanged(tv: TextView, text: CharSequence) {
        when (tv.id) {
            R.id.atcv_destination -> {
                if (text.length >= numCharToSuggest) {
                    if (isInAddresses(text.toString())) img_check_destination.visibility = View.VISIBLE
                    else img_check_destination.visibility = View.GONE
                }
            }
        }
    }

    @Click(R.id.edt_arrival, R.id.edt_departure, R.id.img_add_num_traveler, R.id.img_sub_num_traveler)
    fun onClick(v: View) {
        when (v.id) {
            R.id.edt_arrival -> {
                showCalendar(edt_arrival)
            }
            R.id.edt_departure -> {
                showCalendar(edt_departure)
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

    override fun getSuggestAddressResult(addresses: ArrayList<String>) {
        dismissLoading()
        mDestinations.clear()
        mDestinations.addAll(addresses)
        initAutocompleteTextView()
    }

    override fun createPublicTripResult(trip: PublicTrip) {
        dismissLoading()
        setResult(Activity.RESULT_OK, Intent().putExtra(Constant.PUBLIC_TRIPS, trip))
        clearData()
        finish()
    }

    fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        tv_actionbar_title.text = getString(R.string.create_trip)
        imgv_actionbar_back.setOnClickListener {
            clearData()
            finish()
        }

    }

    fun setViews() {
        img_add_num_traveler.visibility = View.VISIBLE
        img_sub_num_traveler.visibility = View.VISIBLE
        edt_arrival.isFocusable = false
        edt_departure.isFocusable = false
        edt_num_traveler.isFocusable = false
        setNumTravelerTextView()
    }

    fun initAutocompleteTextView() {
        mDestinationAdapter = ArrayAdapter(this, android.R.layout.select_dialog_item, mDestinations)
        atcv_destination.threshold = numCharToSuggest
        atcv_destination.setAdapter(mDestinationAdapter)
    }

    fun setNumTravelerTextView() {
        if (sNumTraveler <= 1) edt_num_traveler.setText(sNumTraveler.toString() + " " + getString(R.string.traveler))
        else edt_num_traveler.setText(sNumTraveler.toString() + " " + getString(R.string.travelers))
    }

    fun showCalendar(v: EditText) {
        val calendar = Calendar.getInstance()

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            when (v.id) {
                R.id.edt_arrival -> {
                    mArrival = calendar
                }
                R.id.edt_departure -> {
                    mDeparture = calendar
                }
            }

            if (isValidDate()) {
                val sdf = SimpleDateFormat(Constant.DATE_FORMAT_SEND, Locale.US)
                v.setText(sdf.format(calendar.time))
            } else showToast(getString(R.string.warning_arrival_must_be_after_departure))
        }

        var dp = DatePickerDialog(this@CreateTripActivity, dateSetListener, year, month, day)
        dp.datePicker.minDate = calendar.getTimeInMillis()
        dp.show()
    }

    /**
     * check if arrival date is after departure date
     * */

    fun isValidDate(): Boolean {
        if (mArrival == null || mDeparture == null) return true
        else {
            if (mArrival!!.after(mDeparture)) return false
            return true
        }
        return false
    }

    /**
     * check if destination is valid (match with one of address in database)
     * */

    fun isInAddresses(address: String): Boolean {
        for (add in mDestinations)
            if (add.equals(address)) return true
        return false
    }

    fun submit() {
        var trip = JsonObject()
        if (!invalidField()) {
            trip.addProperty("Destination", atcv_destination.text.toString())
            trip.addProperty("ArrivalDate", edt_arrival.text.toString())
            trip.addProperty("DepartureDate", edt_departure.text.toString())
            trip.addProperty("TravelerNumber", sNumTraveler)
            trip.addProperty("Description", edt_trip_description.text.toString())
            showLoading()
            mPresenter!!.createPublicTrip(trip)
        }
    }

    fun invalidField(): Boolean {
        if (atcv_destination.text.isEmpty()) {
            showToast(getString(R.string.warning_input_destination))
            return true
        }
        if (img_check_destination.visibility == View.GONE) {
            showToast(getString(R.string.warning_select_destination))
            return true
        }
        if (edt_arrival.text.isEmpty()) {
            showToast(getString(R.string.warning_input_arrival_date))
            return true
        }
        if (edt_departure.text.isEmpty()) {
            showToast(getString(R.string.warning_input_departure_date))
            return true
        }
        return false
    }

    fun clearData() {
        mArrival = null
        mDeparture = null
        sNumTraveler = 1
    }
}