package dut.t2.travelhepler.ui.main.more.friends.waiting_requests

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhelper.service.model.Profile
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.FriendRequest
import dut.t2.travelhepler.ui.hosts.info.HostInfoActivity_
import dut.t2.travelhepler.utils.Constant
import kotlinx.android.synthetic.main.activity_requests_friends.*
import kotlinx.android.synthetic.main.custom_appbar_layout_dark.*
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_requests_friends)
class WaitingFriendRequestsActivity :
    BaseActivity<WaitingFriendRequestsContract.WaitingRequestsView, WaitingFriendRequestsPresenterImpl>(),
    WaitingFriendRequestsContract.WaitingRequestsView {

    private var mRequests: ArrayList<FriendRequest> = ArrayList()
    private lateinit var mAdapter: WaitingFriendRequestAdapter

    override fun initPresenter() {
        mPresenter = WaitingFriendRequestsPresenterImpl(this)
    }

    override fun afterViews() {
        initToolbar()
        initRcv()
        showLoading()
        mPresenter!!.getFriendRequests()
    }

    override fun getFriendRequestsResult(requests: ArrayList<FriendRequest>) {
        if (requests.size == 0) showToast(getString(R.string.can_not_find_friend_request))
        mRequests.clear()
        mRequests.addAll(requests)
        mAdapter.notifyDataSetChanged()
        dismissLoading()
    }

    override fun acceptFriendRequestResult(friendRequest: FriendRequest) {
        showLoading()
        mPresenter!!.getFriendRequests()
    }

    override fun ignoreFriendRequest() {
        showLoading()
        mPresenter!!.getFriendRequests()
    }

    fun initToolbar() {
        setSupportActionBar(toolbar_appbar_dark)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        tv_title_appbar.visibility = View.VISIBLE
        img_back_appbar.visibility = View.VISIBLE
        tv_title_appbar.text = getString(R.string.request_from_traveler)
        img_back_appbar.setOnClickListener { finish() }
    }

    fun initRcv() {
        rcv_requests_friends.setHasFixedSize(true)
        mAdapter = WaitingFriendRequestAdapter(
            this,
            mRequests,
            object : WaitingFriendRequestAdapter.RequestClickListener {

                override fun onClick(sender: Profile) {
                    HostInfoActivity_.intent(this@WaitingFriendRequestsActivity).extra(Constant.HOST, sender).start()
                }

                override fun onPopupItemClick(itemId: Int, request: FriendRequest) {
                    when (itemId) {
                        R.id.item_accept -> {
                            showLoading()
                            mPresenter!!.acceptFriendRequest(request.id)
                        }
                        R.id.item_ignore -> {
                            showLoading()
                            mPresenter!!.ignoreFriendRequest(request.id)
                        }
                    }
                }
            })
        rcv_requests_friends.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcv_requests_friends.adapter = mAdapter
    }
}