package dut.t2.travelhepler.ui.main.more.friends

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhelper.service.model.Profile
import dut.t2.travelhepler.R
import kotlinx.android.synthetic.main.activity_friends.*
import kotlinx.android.synthetic.main.custom_appbar_layout_dark.*
import kotlinx.android.synthetic.main.custom_appbar_layout_dark.img_back_appbar
import kotlinx.android.synthetic.main.custom_appbar_layout_dark.tv_title_appbar
import org.androidannotations.annotations.EActivity

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

            }
        })
        rcv_friends.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcv_friends.adapter = mAdapter
    }
}