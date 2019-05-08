package dut.t2.travelhepler.ui.main.more.friends.my_requests

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhelper.service.model.Profile
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.FriendRequest
import dut.t2.travelhepler.ui.hosts.info.HostInfoActivity_
import dut.t2.travelhepler.ui.main.more.friends.FriendsAdapter
import dut.t2.travelhepler.utils.Constant
import kotlinx.android.synthetic.main.activity_friends.*
import kotlinx.android.synthetic.main.activity_my_friend_requests.*
import kotlinx.android.synthetic.main.custom_appbar_layout_dark.*
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_my_friend_requests)
class MyFriendRequestsActivity :
    BaseActivity<MyFriendRequestsContract.MyFriendRequestsView, MyFriendRequestsPresenterImpl>(),
    MyFriendRequestsContract.MyFriendRequestsView {

    private var mRequests = ArrayList<FriendRequest>()
    private lateinit var mAdapter: MyFriendRequestsAdapter

    override fun initPresenter() {
        mPresenter = MyFriendRequestsPresenterImpl(this)
    }

    override fun afterViews() {
        initToolbar()
        initRcvFriend()
        showLoading()
        mPresenter!!.getMyFriendRequests()
    }

    override fun getMyFriendRequestReuslt(requests: ArrayList<FriendRequest>) {
        mRequests.clear()
        mRequests.addAll(requests)
        mAdapter.notifyDataSetChanged()
        dismissLoading()
    }

    override fun cancelFriendRequestResult() {
        showLoading()
        mPresenter!!.getMyFriendRequests()
    }

    fun initToolbar() {
        setSupportActionBar(toolbar_appbar_dark)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        tv_title_appbar.visibility = View.VISIBLE
        img_back_appbar.visibility = View.VISIBLE
        tv_title_appbar.text = getString(R.string.my_friend_request)
        img_back_appbar.setOnClickListener { finish() }
    }

    fun initRcvFriend() {
        rcv_my_friend_requests.setHasFixedSize(true)
        mAdapter = MyFriendRequestsAdapter(this, mRequests, object : MyFriendRequestsAdapter.RequestClickListener {
            override fun onClick(friend: Profile) {
                HostInfoActivity_.intent(this@MyFriendRequestsActivity).extra(Constant.HOST, friend).start()
            }

            override fun onPopupItemClick(itemId: Int, request: FriendRequest) {
                when (itemId) {
                    R.id.item_cancel -> {
                        showLoading()
                        mPresenter!!.cancelFriendRequest(request.id)
                    }
                }
            }
        })
        rcv_my_friend_requests.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcv_my_friend_requests.adapter = mAdapter
    }
}