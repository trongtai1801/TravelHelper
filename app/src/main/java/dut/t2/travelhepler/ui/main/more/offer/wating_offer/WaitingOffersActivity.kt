package dut.t2.travelhepler.ui.main.more.offer.wating_offer

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhelper.service.model.Profile
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.Offer
import dut.t2.travelhepler.ui.hosts.info.HostInfoActivity_
import dut.t2.travelhepler.utils.Constant
import kotlinx.android.synthetic.main.activity_offers.*
import kotlinx.android.synthetic.main.custom_appbar_layout_dark.*
import org.androidannotations.annotations.EActivity


@EActivity(R.layout.activity_offers)
class WaitingOffersActivity :
    BaseActivity<WaitingOfferContract.WaitingOffersView, WaitingOffersPresenterImpl>(),
    WaitingOfferContract.WaitingOffersView {

    private var mOffers: ArrayList<Offer> = ArrayList()
    private lateinit var mAdapter: WaitingOffersAdapter

    override fun initPresenter() {
        mPresenter = WaitingOffersPresenterImpl(this)
    }

    override fun afterViews() {
        initToolbar()
        initRcv()
        swf_my_offers.setOnRefreshListener { mPresenter!!.getOfferToHost() }
        showLoading()
        mPresenter!!.getOfferToHost()
    }

    override fun getOfferToHostResult(offers: ArrayList<Offer>) {
        mOffers.clear()
        mOffers.addAll(offers)
        mAdapter.notifyDataSetChanged()
        dismissLoading()
        if (swf_my_offers.isRefreshing) swf_my_offers.isRefreshing = false
    }

    fun initToolbar() {
        setSupportActionBar(toolbar_appbar_dark)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        tv_title_appbar.visibility = View.VISIBLE
        img_back_appbar.visibility = View.VISIBLE
        tv_title_appbar.text = getString(R.string.offer_from_host)
        img_back_appbar.setOnClickListener { finish() }
    }

    fun initRcv() {
        rcv_my_offers.setHasFixedSize(true)
        mAdapter = WaitingOffersAdapter(
            this,
            mOffers,
            object : WaitingOffersAdapter.OfferClickListener {
                override fun onClick(offer: Offer) {
                    showToast("click " + offer.id)
                }

                override fun onDelete(offer: Offer) {
                    showToast("delete " + offer.id)
                }

                override fun onSenderClick(sender: Profile) {
                    HostInfoActivity_.intent(this@WaitingOffersActivity).extra(Constant.HOST, sender).start()
                }
            })
        rcv_my_offers.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcv_my_offers.adapter = mAdapter
    }
}