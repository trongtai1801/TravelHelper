package dut.t2.travelhepler.ui.main.dashboard

import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.PublicTrip
import dut.t2.travelhepler.service.model.SearchItem
import dut.t2.travelhepler.ui.main.MainActivity
import dut.t2.travelhepler.ui.trips.info.InfoActivity_
import dut.t2.travelhepler.utils.Constant
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EFragment

@EFragment(R.layout.fragment_dashboard)
class DashboardFragment : Fragment() {
    lateinit var searchAdapter: SearchAdapter
    lateinit var publicTripAdapter: PublicTripAdapter
    var searchItems: ArrayList<SearchItem> = ArrayList()

    var mPublicTrips: ArrayList<PublicTrip> = ArrayList()

    @AfterViews
    fun afterViews() {
        mPublicTrips.clear()
        mPublicTrips.addAll(arguments!!.get(Constant.PUBLIC_TRIPS) as Collection<PublicTrip>)
        initSearchRcv()
        initPublicTripsRcv()
        swf_dashboard.setOnRefreshListener {
            (activity as MainActivity).getPublicTrips()
        }
    }

    override fun onResume() {
        super.onResume()
        if (mPublicTrips.size <= 0) {
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
        rcv_public_trip_dashboard.setHasFixedSize(true)
        publicTripAdapter = PublicTripAdapter(context!!, mPublicTrips, object : PublicTripAdapter.ItemClickListener {
            override fun onClick(publicTrip: PublicTrip) {
                InfoActivity_.intent(context).extra(Constant.PUBLIC_TRIPS, publicTrip).start()
            }
        })
        rcv_public_trip_dashboard.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rcv_public_trip_dashboard.adapter = publicTripAdapter
    }

    fun dismissSwipeRefreshLayout() {
        if (swf_dashboard != null && swf_dashboard.isRefreshing)
            swf_dashboard.isRefreshing = false
    }
}