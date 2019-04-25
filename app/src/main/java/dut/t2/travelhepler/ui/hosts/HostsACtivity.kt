package dut.t2.travelhepler.ui.hosts

import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhepler.R
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_hosts)
class HostsACtivity : BaseActivity<HostsContrct.HostsView, HostsPresenterImpl>(),
    HostsContrct.HostsView {
    override fun initPresenter() {
        mPresenter = HostsPresenterImpl(this)
    }

    override fun afterViews() {

    }
}