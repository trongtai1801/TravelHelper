package dut.t2.travelhepler.ui.main.dashboard

import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.DashboarItem
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EFragment

@EFragment(R.layout.fragment_dashboard)
class DashboardFragment :  Fragment(){
    lateinit var dashboardAdapter: DashboardAdapter
    var dashBoardItems: ArrayList<DashboarItem> = ArrayList()

    @AfterViews
    fun afterViews() {
        initRcv()
    }

    fun initRcv() {
        dashBoardItems.clear()
        dashBoardItems.add(
            DashboarItem(
                "Travel",
                "http://travelhelperwebsite.azurewebsites.net/Images/quan.jpg?fbclid=IwAR1l7dDsXIOuc5ejMSTWvfGCkowlyjy6ztPPo4Tk_h4Lo4Nb9O9mA6m4eLw"
            )
        )
        dashBoardItems.add(
            DashboarItem(
                "Host",
                "http://travelhelperwebsite.azurewebsites.net/Images/quan.jpg?fbclid=IwAR1l7dDsXIOuc5ejMSTWvfGCkowlyjy6ztPPo4Tk_h4Lo4Nb9O9mA6m4eLw"
            )
        )
        dashBoardItems.add(
            DashboarItem(
                "Event",
                "http://travelhelperwebsite.azurewebsites.net/Images/quan.jpg?fbclid=IwAR1l7dDsXIOuc5ejMSTWvfGCkowlyjy6ztPPo4Tk_h4Lo4Nb9O9mA6m4eLw"
            )
        )
        rcv_dashboard.setHasFixedSize(true)
        dashboardAdapter = DashboardAdapter(context!!, dashBoardItems, object : DashboardAdapter.ItemClickListener {
            override fun onClick(dashboarItem: DashboarItem) {
                Toast.makeText(context, dashboarItem.name, Toast.LENGTH_LONG).show()
            }
        })
        rcv_dashboard.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rcv_dashboard.adapter = dashboardAdapter
    }
}