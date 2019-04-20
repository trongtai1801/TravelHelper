package dut.t2.travelhepler.ui.main.dashboard

import android.app.Activity
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.PublicTrip
import dut.t2.travelhepler.service.model.SearchItem
import dut.t2.travelhepler.ui.main.MainActivity
import dut.t2.travelhepler.ui.trips.create.CreateTripActivity_
import dut.t2.travelhepler.ui.trips.info.InfoActivity_
import dut.t2.travelhepler.utils.Constant
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EFragment

@EFragment(R.layout.fragment_dashboard)
class DashboardFragment : Fragment() {
    lateinit var mSearchAdapter: SearchAdapter
    lateinit var mPublicTripAdapter: PublicTripAdapter
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

    @Click(R.id.tv_create_trip_dashboard)
    fun onClick(v: View) {
        when (v.id) {
            R.id.tv_create_trip_dashboard -> {
                CreateTripActivity_.intent(context).startForResult(Constant.REQUEST_CODE_CREATE_PUBLIC_TRIP)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.REQUEST_CODE_CREATE_PUBLIC_TRIP && resultCode == Activity.RESULT_OK && data != null) {
            var trip = data.getParcelableExtra<PublicTrip>(Constant.PUBLIC_TRIPS)
            mPublicTrips.add(trip)
            mPublicTripAdapter.notifyDataSetChanged()
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
        mSearchAdapter = SearchAdapter(context!!, searchItems, object : SearchAdapter.ItemClickListener {
            override fun onClick(searchItem: SearchItem) {
                Toast.makeText(context, searchItem.name, Toast.LENGTH_LONG).show()
            }
        })
        rcv_search_dashboard.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rcv_search_dashboard.adapter = mSearchAdapter
    }

    fun initPublicTripsRcv() {
        rcv_public_trip_dashboard.setHasFixedSize(true)
        mPublicTripAdapter = PublicTripAdapter(context!!, mPublicTrips, object : PublicTripAdapter.ItemClickListener {
            override fun onClick(publicTrip: PublicTrip) {
                InfoActivity_.intent(context).extra(Constant.PUBLIC_TRIPS, publicTrip).start()
            }
        })
        rcv_public_trip_dashboard.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rcv_public_trip_dashboard.adapter = mPublicTripAdapter

        showPublicTrips()
    }

    fun dismissSwipeRefreshLayout() {
        if (swf_dashboard != null && swf_dashboard.isRefreshing)
            swf_dashboard.isRefreshing = false
    }

    fun showPublicTrips() {
        if (mPublicTrips.size <= 0) {
            tv_no_upcoming_dashboard.visibility = View.VISIBLE
            rcv_public_trip_dashboard.visibility = View.GONE
        } else {
            tv_no_upcoming_dashboard.visibility = View.GONE
            rcv_public_trip_dashboard.visibility = View.VISIBLE
        }
    }
}