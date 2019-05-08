package dut.t2.travelhepler.ui.main.more.requests.my_requests

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
class MyRequestsActivity : BaseActivity<MyRequestsContract.MyRequestsView, MyRequestsPresenterImpl>(),
    MyRequestsContract.MyRequestsView {

    private var mRequests: ArrayList<Request> = ArrayList()
    private lateinit var mAdapter: MyRequestsAdapter

    override fun initPresenter() {
        mPresenter = MyRequestsPresenterImpl(this)
    }

    override fun afterViews() {
        initToolbar()
        initRcv()
        swf_my_requests.setOnRefreshListener { mPresenter!!.getMyRequests() }
        showLoading()
        mPresenter!!.getMyRequests()
    }

    override fun getMyRequestsResult(requests: ArrayList<Request>) {
        if (requests != null) {
            mRequests.clear()
            mRequests.addAll(requests)
            mAdapter.notifyDataSetChanged()
        }
        dismissLoading()
        if (swf_my_requests.isRefreshing) swf_my_requests.isRefreshing = false
    }

    override fun cancelMyRequestResult() {
        showLoading()
        mPresenter!!.getMyRequests()
    }

    fun initToolbar() {
        setSupportActionBar(toolbar_appbar_dark)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        tv_title_appbar.visibility = View.VISIBLE
        img_back_appbar.visibility = View.VISIBLE
        tv_title_appbar.text = getString(R.string.my_request)
        img_back_appbar.setOnClickListener { finish() }

    }

    fun initRcv() {
        rcv_my_requests.setHasFixedSize(true)
        mAdapter = MyRequestsAdapter(
            this,
            mRequests,
            object : MyRequestsAdapter.RequestClickListener {
                override fun onClick(request: Request) {

                }

                override fun onDelete(request: Request) {

                }

                override fun onReceiverClick(receiver: Profile) {
                    HostInfoActivity_.intent(this@MyRequestsActivity).extra(Constant.HOST, receiver).start()
                }

                override fun onPopupItemClick(itemId: Int, request: Request) {
                    showLoading()
                    mPresenter!!.cancelMyRequest(request.id)
                }
            })
        rcv_my_requests.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcv_my_requests.adapter = mAdapter
    }
}