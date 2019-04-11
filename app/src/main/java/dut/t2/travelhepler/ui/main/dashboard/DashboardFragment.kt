package dut.t2.travelhepler.ui.main.dashboard

import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.PublicTrip
import dut.t2.travelhepler.service.model.SearchItem
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EFragment

@EFragment(R.layout.fragment_dashboard)
class DashboardFragment :  Fragment(){
    lateinit var searchAdapter: SearchAdapter
    lateinit var publicTripAdapter: PublicTripAdapter
    var searchItems: ArrayList<SearchItem> = ArrayList()
    var publicTrips: ArrayList<PublicTrip> = ArrayList()

    @AfterViews
    fun afterViews() {
        initSearchRcv()
        initPublicTripsRcv()
    }

    override fun onResume() {
        super.onResume()
        if (publicTrips.size <= 0) {
            tv_no_upcoming_dashboard.visibility = View.VISIBLE
            rcv_public_trip_dashboard.visibility = View.GONE
        } else {
            tv_no_upcoming_dashboard.visibility = View.GONE
            rcv_public_trip_dashboard.visibility = View.VISIBLE
        }
    }

    @Click(R.id.tv_create_trip_dashboard)
    fun onClick(v: View) {
        when (v.id) {
            R.id.tv_create_trip_dashboard -> {
                Toast.makeText(context!!, "Create", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun initSearchRcv() {
        searchItems.clear()
        searchItems.add(
            SearchItem(
                "Travelers",
                "http://travelhelperwebsite.azurewebsites.net/Images/quan.jpg?fbclid=IwAR1l7dDsXIOuc5ejMSTWvfGCkowlyjy6ztPPo4Tk_h4Lo4Nb9O9mA6m4eLw"
            )
        )
        searchItems.add(
            SearchItem(
                "Hosts",
                "http://travelhelperwebsite.azurewebsites.net/Images/quan.jpg?fbclid=IwAR1l7dDsXIOuc5ejMSTWvfGCkowlyjy6ztPPo4Tk_h4Lo4Nb9O9mA6m4eLw"
            )
        )
        searchItems.add(
            SearchItem(
                "Events",
                "http://travelhelperwebsite.azurewebsites.net/Images/quan.jpg?fbclid=IwAR1l7dDsXIOuc5ejMSTWvfGCkowlyjy6ztPPo4Tk_h4Lo4Nb9O9mA6m4eLw"
            )
        )
        rcv_search_dashboard.setHasFixedSize(true)
        searchAdapter = SearchAdapter(context!!, searchItems, object : SearchAdapter.ItemClickListener {
            override fun onClick(searchItem: SearchItem) {
                Toast.makeText(context, searchItem.name, Toast.LENGTH_LONG).show()
            }
        })
        rcv_search_dashboard.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rcv_search_dashboard.adapter = searchAdapter
    }

    fun initPublicTripsRcv() {
        publicTrips.clear()
        publicTrips.add(
            PublicTrip("2019.04.30", "2019.01.05", "Viet Nam", 2)
        )
        publicTrips.add(
            PublicTrip("2019.04.30", "2019.01.05", "Canada", 3)
        )
        rcv_public_trip_dashboard.setHasFixedSize(true)
        publicTripAdapter = PublicTripAdapter(context!!, publicTrips, object : PublicTripAdapter.ItemClickListener {
            override fun onClick(publicTrip: PublicTrip) {
                Toast.makeText(context, publicTrip.destination, Toast.LENGTH_LONG).show()
            }
        })
        rcv_public_trip_dashboard.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rcv_public_trip_dashboard.adapter = publicTripAdapter
    }
}