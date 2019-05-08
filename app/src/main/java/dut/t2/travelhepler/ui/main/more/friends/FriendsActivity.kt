package dut.t2.travelhepler.ui.main.more.friends

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.microsoft.signalr.HubConnection
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhelper.service.model.Profile
import dut.t2.travelhepler.R
import dut.t2.travelhepler.ui.hosts.info.HostInfoActivity_
import dut.t2.travelhepler.ui.main.more.friends.search_friend.SearchFriendActivity_
import dut.t2.travelhepler.ui.main.more.friends.search_friend.search_result.SearchFriendResultActivity_
import dut.t2.travelhepler.utils.Constant
import kotlinx.android.synthetic.main.activity_friends.*
import kotlinx.android.synthetic.main.custom_appbar_layout_dark.*
import kotlinx.android.synthetic.main.custom_appbar_layout_dark.img_back_appbar
import kotlinx.android.synthetic.main.custom_appbar_layout_dark.tv_title_appbar
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.OnActivityResult

@EActivity(R.layout.activity_friends)
class FriendsActivity : BaseActivity<FriendsContract.FriendsView, FriendsPresenterImpl>(),
    FriendsContract.FriendsView {

    private var mFriends = ArrayList<Profile>()
    private lateinit var mAdapter: FriendsAdapter

    override fun initPresenter() {
        mPresenter = FriendsPresenterImpl(this)
    }

    override fun afterViews() {
        initToolbar()
        initRcvFriend()
        showLoading()
        mPresenter!!.getFriends()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.item_search -> {
                SearchFriendActivity_.intent(this).startForResult(Constant.REQUEST_CODE_GET_SEARCH_USER_STRING)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @OnActivityResult(Constant.REQUEST_CODE_GET_SEARCH_USER_STRING)
    fun onResult(resultCode: Int, data: Intent) {
        if (resultCode == Activity.RESULT_OK) {
            var searchString = data!!.getStringExtra(Constant.SEARCH_USER_STRING)
            showLoading()
            mPresenter!!.searchFriend(searchString)
        }
    }

    override fun getFriendsResult(friends: ArrayList<Profile>) {
        if (friends.size == 0) showToast(getString(R.string.can_not_find_out_friend))
        mFriends.clear()
        mFriends.addAll(friends)
        mAdapter.notifyDataSetChanged()
        dismissLoading()
    }

    override fun searchFriendResult(users: ArrayList<Profile>) {
        dismissLoading()
        SearchFriendResultActivity_.intent(this).extra(Constant.USERS, users).start()
    }

    fun initToolbar() {
        setSupportActionBar(toolbar_appbar_dark)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        tv_title_appbar.visibility = View.VISIBLE
        img_back_appbar.visibility = View.VISIBLE
        tv_title_appbar.text = getString(R.string.friend)
        img_back_appbar.setOnClickListener { finish() }
    }

    fun initRcvFriend() {
        rcv_friends.setHasFixedSize(true)
        mAdapter = FriendsAdapter(this, mFriends, object : FriendsAdapter.FriendsClickListener {
            override fun onClick(friend: Profile) {
                HostInfoActivity_.intent(this@FriendsActivity).extra(Constant.HOST, friend).start()
            }

            override fun onPopupItemClick(itemId: Int, friend: Profile) {
                when (itemId) {
                    R.id.item_message -> {
                        showToast("message to " + friend.id)
                    }
                    R.id.item_unfriend -> {
                        showToast("unfriend with " + friend.id)
                    }
                }
            }
        })
        rcv_friends.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcv_friends.adapter = mAdapter
    }
}