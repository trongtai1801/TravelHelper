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
    private lateinit var mAdapterMy: MyRequestsAdapter

    companion object {
        var sFlag = -1
    }

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
            mAdapterMy.notifyDataSetChanged()
        }
        dismissLoading()
        if (swf_my_requests.isRefreshing) swf_my_requests.isRefreshing = false
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
        mAdapterMy = MyRequestsAdapter(
            this,
            mRequests,
            object : MyRequestsAdapter.RequestClickListener {
                override fun onClick(request: Request) {
                    showToast("click " + request.id)
                }

                override fun onDelete(request: Request) {
                    showToast("delete " + request.id)
                }

                override fun onReceiverClick(receiver: Profile) {
                    HostInfoActivity_.intent(this@MyRequestsActivity).extra(Constant.HOST, receiver).start()
                }
            })
        rcv_my_requests.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcv_my_requests.adapter = mAdapterMy
    }
}