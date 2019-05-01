package dut.t2.travelhepler.ui.travelers.offer

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.google.gson.JsonObject
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhelper.service.model.Profile
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.PublicTrip
import dut.t2.travelhepler.ui.hosts.request.RequestToStayActivity
import dut.t2.travelhepler.utils.CalendarUtils
import dut.t2.travelhepler.utils.Constant
import kotlinx.android.synthetic.main.activity_request_to_stay.*
import kotlinx.android.synthetic.main.custom_appbar_layout_dark.*
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_request_to_stay)
class OfferActivity : BaseActivity<OfferContract.OfferView, OfferPresenterImpl>(),
    OfferContract.OfferView {

    private var mTrip: PublicTrip? = null
    private var mTraveler: Profile? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mTrip = intent.getParcelableExtra(Constant.TRAVELER)
        mTraveler = mTrip!!.user
    }

    override fun initPresenter() {
        mPresenter = OfferPresenterImpl(this)
    }

    override fun afterViews() {
        initToolbar()
        setupView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_offer, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.item_offer -> {
                submit()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun createOfferResult() {
        showToast(getString(R.string.offered))
        dismissLoading()
    }

    fun initToolbar() {
        setSupportActionBar(toolbar_appbar_dark)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        tv_title_appbar.visibility = View.VISIBLE
        img_back_appbar.visibility = View.VISIBLE
        tv_title_appbar.text = getString(R.string.offer_to_host)
        img_back_appbar.setOnClickListener { onBackPressed() }
    }

    fun setupView() {
        img_add_num_traveler.visibility = View.GONE
        img_sub_num_traveler.visibility = View.GONE
        Glide.with(this).load(mTraveler)
            .placeholder(this.getDrawable(R.drawable.ic_user_circle))
            .into(cir_img_host_avatar)
        tv_host_name.text = mTraveler!!.fullName
        tv_host_address.text = mTraveler!!.address
        tv_arrival.text = CalendarUtils.convertStringFormat(mTrip!!.splitArrivalDate())
        tv_departure.text = CalendarUtils.convertStringFormat(mTrip!!.splitDepartureDate())
        tv_num_traveler.text = mTrip!!.travelerNumber.toString() + " " + getString(R.string.travelers)
    }

    fun submit() {
        var trip = JsonObject()
        trip.addProperty("ArrivalDate", CalendarUtils.convertStringFormat(mTrip!!.arrivalDate))
        trip.addProperty("DepartureDate", CalendarUtils.convertStringFormat(mTrip!!.departureDate))
        trip.addProperty("TravelerNumber", mTrip!!.travelerNumber)
        trip.addProperty("Message", "")
        showLoading()
        mPresenter!!.createOffer(mTraveler!!.id, trip)
    }
}