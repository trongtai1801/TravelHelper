package dut.t2.travelhepler.ui.main.more.requests.wating_requests

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhelper.service.model.Profile
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.Request
import dut.t2.travelhepler.ui.hosts.info.HostInfoActivity_
import dut.t2.travelhepler.utils.Constant
import kotlinx.android.synthetic.main.activity_requests.*
import kotlinx.android.synthetic.main.custom_appbar_layout_dark.*
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_requests)
class WaitingRequestsActivity :
    BaseActivity<WaitingRequestsContract.WaitingRequestsView, WaitingRequestsPresenterImpl>(),
    WaitingRequestsContract.WaitingRequestsView {

    private var mRequests: ArrayList<Request> = ArrayList()
    private lateinit var mAdapter: WaitingRequestsAdapter

    override fun initPresenter() {
        mPresenter = WaitingRequestsPresenterImpl(this)
    }

    override fun afterViews() {
        initToolbar()
        initRcv()
        swf_my_requests.setOnRefreshListener {
            mPresenter!!.getTravelerRequests()
        }
        showLoading()
        mPresenter!!.getTravelerRequests()
    }

    override fun getTravelerRequests(requests: ArrayList<Request>) {
        mRequests.clear()
        mRequests.addAll(requests)
        mAdapter.notifyDataSetChanged()
        dismissLoading()
    }


    fun initToolbar() {
        setSupportActionBar(toolbar_appbar_dark)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        tv_title_appbar.visibility = View.VISIBLE
        img_back_appbar.visibility = View.VISIBLE
        tv_title_appbar.text = getString(R.string.request_to_stay)
        img_back_appbar.setOnClickListener { finish() }

    }

    fun initRcv() {
        rcv_my_requests.setHasFixedSize(true)
        mAdapter = WaitingRequestsAdapter(
            this,
            mRequests,
            object : WaitingRequestsAdapter.RequestClickListener {
                override fun onClick(request: Request) {
                    showToast("click " + request.id)
                }

                override fun onDelete(request: Request) {
                    showToast("delete " + request.id)
                }

                override fun onReceiverClick(receiver: Profile) {
                    HostInfoActivity_.intent(this@WaitingRequestsActivity).extra(Constant.HOST, receiver).start()
                }
            })
        rcv_my_requests.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcv_my_requests.adapter = mAdapter
    }
}