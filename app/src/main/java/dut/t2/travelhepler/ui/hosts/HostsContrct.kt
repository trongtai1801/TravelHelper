package dut.t2.travelhepler.ui.hosts

import dut.t2.travelhelper.base.BaseView
import dut.t2.travelhelper.service.model.Profile
import dut.t2.travelhepler.service.model.Home

interface HostsContrct {

    interface HostsView : BaseView {
        fun getHostsResult(hosts: ArrayList<Profile>)
    }

    interface HostsPresenter {
        fun getHosts(address: String)
    }
}