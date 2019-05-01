package dut.t2.travelhepler.ui.main.more.offer.my_offer

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhelper.service.model.Profile
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.Offer
import dut.t2.travelhepler.ui.hosts.info.HostInfoActivity_
import dut.t2.travelhepler.ui.travelers.info.TravelerInfoActivity_
import dut.t2.travelhepler.utils.Constant
import kotlinx.android.synthetic.main.activity_offers.*
import kotlinx.android.synthetic.main.custom_appbar_layout_dark.*
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_offers)
class MyOffersActivity : BaseActivity<MyOffersContract.MyOffersView, MyOffersPresenterImpl>(),
    MyOffersContract.MyOffersView {

    private var mOffer: ArrayList<Offer> = ArrayList()
    private lateinit var mAdapter: MyOffersAdapter

    override fun initPresenter() {
        mPresenter = MyOffersPresenterImpl(this)
    }

    override fun afterViews() {
        initToolbar()
        initRcv()
        swf_my_offers.setOnRefreshListener { mPresenter!!.getMyOffer() }
        showLoading()
        mPresenter!!.getMyOffer()
    }

    override fun getMyOfferResult(offers: ArrayList<Offer>) {
        mOffer.clear()
        mOffer.addAll(offers)
        mAdapter.notifyDataSetChanged()
        dismissLoading()
    }

    fun initToolbar() {
        setSupportActionBar(toolbar_appbar_dark)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        tv_title_appbar.visibility = View.VISIBLE
        img_back_appbar.visibility = View.VISIBLE
        tv_title_appbar.text = getString(R.string.my_offer)
        img_back_appbar.setOnClickListener { finish() }

    }

    fun initRcv() {
        rcv_my_offers.setHasFixedSize(true)
        mAdapter = MyOffersAdapter(
            this,
            mOffer,
            object : MyOffersAdapter.OfferClickListener {
                override fun onClick(offer: Offer) {
                    showToast("click " + offer.id)
                }

                override fun onDelete(offer: Offer) {
                    showToast("delete " + offer.id)
                }

                override fun onReceiverClick(receiver: Profile) {
                    HostInfoActivity_.intent(this@MyOffersActivity).extra(Constant.HOST, receiver).start()
                }
            })
        rcv_my_offers.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcv_my_offers.adapter = mAdapter
    }
}