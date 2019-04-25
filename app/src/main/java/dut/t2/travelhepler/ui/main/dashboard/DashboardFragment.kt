package dut.t2.travelhepler.ui.main.dashboard

import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.PublicTrip
import dut.t2.travelhepler.service.model.SearchItem
import dut.t2.travelhepler.ui.hosts.HostsActivity_
import dut.t2.travelhepler.ui.main.MainActivity
import dut.t2.travelhepler.ui.trips.create.CreateTripActivity_
import dut.t2.travelhepler.ui.trips.info.InfoActivity_
import dut.t2.travelhepler.ui.trips.update.UpdateTripActivity_
import dut.t2.travelhepler.utils.Constant
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EFragment

@EFragment(R.layout.fragment_dashboard)
class DashboardFragment : Fragment() {
    lateinit var mSearchAdapter: SearchAdapter
    var mPublicTripAdapter: PublicTripAdapter? = null
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

    fun initSearchRcv() {
        searchItems.clear()
        searchItems.add(
            SearchItem(
                Constant.ID_SEARCH_ITEM_HOST,
                "Hosts",
                "http://travelhelperwebsite.azurewebsites.net/images/taichodien.jpg"
            )
        )
        searchItems.add(
            SearchItem(
                Constant.ID_SEARCH_ITEM_TRAVELERS,
                "Travelers",
                "http://travelhelperwebsite.azurewebsites.net/Images/tai1.jpg"
            )
        )
        searchItems.add(
            SearchItem(
                Constant.ID_SEARCH_ITEM_EVENT,
                "Events",
                "http://travelhelperwebsite.azurewebsites.net/Images/tai2.jpg"
            )
        )
        rcv_search_dashboard.setHasFixedSize(true)
        mSearchAdapter = SearchAdapter(context!!, searchItems, object : SearchAdapter.ItemClickListener {
            override fun onClick(searchItem: SearchItem) {
                when (searchItem.id) {
                    Constant.ID_SEARCH_ITEM_HOST -> {
                        HostsActivity_.intent(context).start()
                    }
                    Constant.ID_SEARCH_ITEM_TRAVELERS -> {

                    }
                }
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

            override fun onPopupItemClick(itemId: Int, trip: PublicTrip) {
                when (itemId) {
                    R.id.item_edit -> {
                        UpdateTripActivity_.intent(context).extra(Constant.PUBLIC_TRIPS, trip)
                            .startForResult(Constant.REQUEST_CODE_UPDATE_PUBLIC_TRIP)
                    }
                    R.id.item_delete -> {
                        (activity as MainActivity).showConfirmDialog(trip.id)
                    }
                }
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

    fun notifyDataSetChanged(publicTrips: ArrayList<PublicTrip>) {
        if (mPublicTrips != null) {
            mPublicTrips.clear()
            mPublicTrips.addAll(publicTrips)
        }
        if (mPublicTripAdapter != null) mPublicTripAdapter!!.notifyDataSetChanged()
    }
}