package dut.t2.travelhepler.ui.hosts

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhelper.service.model.Profile
import dut.t2.travelhepler.R
import dut.t2.travelhepler.ui.hosts.info.HostInfoActivity_
import dut.t2.travelhepler.utils.Constant
import dut.t2.travelhepler.utils.RealmDAO
import kotlinx.android.synthetic.main.activity_hosts.*
import kotlinx.android.synthetic.main.custom_appbar_layout_dark.*
import kotlinx.android.synthetic.main.custom_appbar_layout_dark.img_back_appbar
import kotlinx.android.synthetic.main.custom_appbar_layout_dark.tv_title_appbar
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_hosts)
class HostsActivity : BaseActivity<HostsContrct.HostsView, HostsPresenterImpl>(),
    HostsContrct.HostsView {

    private var mHosts = ArrayList<Profile>()
    private var mAdapter: HostsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHosts.clear()
        mHosts.addAll(intent.getParcelableArrayListExtra(Constant.HOSTS))
    }

    override fun initPresenter() {
        mPresenter = HostsPresenterImpl(this)
    }

    override fun afterViews() {
        initToolbar()
        initRcv()
//        swf_hosts.setOnRefreshListener { mPresenter!!.getHosts(RealmDAO.getProfileLogin()!!.address.split(",")[1].trim()) }
    }

    override fun getHostsResult(hosts: ArrayList<Profile>) {
        if (hosts != null) {
            if (hosts.size > 0) {
                mHosts.clear()
                mHosts.addAll(hosts)
                mAdapter!!.notifyDataSetChanged()
            } else showToast(getString(R.string.data_null))
        }
        tv_total_result_hosts.text = mHosts.size.toString() + " " + getString(R.string.host)
        dismissLoading()
//        if (swf_hosts.isRefreshing) swf_hosts.isRefreshing = false
    }

    fun initToolbar() {
        setSupportActionBar(toolbar_appbar_dark)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        tv_title_appbar.visibility = View.VISIBLE
        img_back_appbar.visibility = View.VISIBLE
        tv_title_appbar.text = getString(R.string.host)
        img_back_appbar.setOnClickListener { onBackPressed() }
    }

    fun initRcv() {
        var context: Context = this
        tv_total_result_hosts.text = mHosts.size.toString() + " " + getString(R.string.host)
        rcv_hosts.setHasFixedSize(true)
        mAdapter = HostsAdapter(this, mHosts, object : HostsAdapter.HostClickListener {
            override fun onClick(host: Profile) {
                HostInfoActivity_.intent(context).extra(Constant.HOST, host).start()
            }
        })
        rcv_hosts.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcv_hosts.adapter = mAdapter
    }
}