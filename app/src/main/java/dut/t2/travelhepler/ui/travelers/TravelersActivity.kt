package dut.t2.travelhepler.ui.travelers

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhelper.service.model.Profile
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.PublicTrip
import dut.t2.travelhepler.ui.hosts.HostsAdapter
import dut.t2.travelhepler.ui.hosts.info.HostInfoActivity_
import dut.t2.travelhepler.ui.travelers.info.TravelerInfoActivity_
import dut.t2.travelhepler.utils.Constant
import kotlinx.android.synthetic.main.activity_hosts.*
import kotlinx.android.synthetic.main.custom_appbar_layout_dark.*
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_hosts)
class TravelersActivity : BaseActivity<TravelersContract.TravelersView, TravelersPresenterImpl>(),
    TravelersContract.TravelersView {

    private var mTravelers = ArrayList<PublicTrip>()
    private var mAdapter: TravlersAdapter? = null

    override fun initPresenter() {
        mPresenter = TravelersPresenterImpl(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mTravelers.clear()
        mTravelers.addAll(intent.getParcelableArrayListExtra(Constant.HOSTS))
    }

    override fun afterViews() {
        initToolbar()
        initRcv()
    }

    fun initToolbar() {
        setSupportActionBar(toolbar_appbar_dark)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        tv_title_appbar.visibility = View.VISIBLE
        img_back_appbar.visibility = View.VISIBLE
        tv_title_appbar.text = getString(R.string.travelers)
        img_back_appbar.setOnClickListener { onBackPressed() }
    }

    fun initRcv() {
        var context: Context = this
        tv_total_result_hosts.text = mTravelers.size.toString() + " " + getString(R.string.travelers)
        rcv_hosts.setHasFixedSize(true)
        mAdapter = TravlersAdapter(this, mTravelers, object : TravlersAdapter.HostClickListener {
            override fun onClick(traveler: PublicTrip) {
                TravelerInfoActivity_.intent(context).extra(Constant.TRAVELER, traveler).start()
            }
        })
        rcv_hosts.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcv_hosts.adapter = mAdapter
    }
}