package dut.t2.travelhepler.ui.main.more.friends.search_friend.search_result

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhelper.service.model.Profile
import dut.t2.travelhepler.R
import dut.t2.travelhepler.ui.hosts.HostsAdapter
import dut.t2.travelhepler.ui.hosts.info.HostInfoActivity_
import dut.t2.travelhepler.utils.Constant
import kotlinx.android.synthetic.main.activity_hosts.*
import kotlinx.android.synthetic.main.custom_appbar_layout_dark.*
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_search_friend_reuslt)
class SearchFriendResultActivity :
    BaseActivity<SearchFriendResultContract.SearchFriendResultView, SearchFriendResultPresenterImpl>(),
    SearchFriendResultContract.SearchFriendResultView {

    private var mHosts = ArrayList<Profile>()
    private lateinit var mAdapter: SearchFriendResultAdapter
    override fun initPresenter() {
        mPresenter = SearchFriendResultPresenterImpl(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHosts.clear()
        mHosts.addAll(intent.getParcelableArrayListExtra(Constant.USERS))
        if (mHosts.size <= 0) showToast(getString(R.string.result_is_empty))
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
        tv_title_appbar.text = getString(R.string.search_friend_result)
        img_back_appbar.setOnClickListener { onBackPressed() }
    }

    fun initRcv() {
        var context: Context = this
        rcv_hosts.setHasFixedSize(true)
        mAdapter = SearchFriendResultAdapter(this, mHosts, object : SearchFriendResultAdapter.HostClickListener {
            override fun onClick(host: Profile) {
                HostInfoActivity_.intent(context).extra(Constant.HOST, host).start()
            }
        })
        rcv_hosts.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcv_hosts.adapter = mAdapter
    }
}