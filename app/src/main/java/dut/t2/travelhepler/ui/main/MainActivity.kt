package dut.t2.travelhepler.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.View
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.PublicTrip
import dut.t2.travelhepler.ui.main.dashboard.DashboardFragment
import dut.t2.travelhepler.ui.main.dashboard.DashboardFragment_
import dut.t2.travelhepler.ui.main.more.MoreFragment
import dut.t2.travelhepler.ui.main.more.MoreFragment_
import dut.t2.travelhepler.ui.main.search.SearchFragment
import dut.t2.travelhepler.ui.main.search.SearchFragment_
import dut.t2.travelhepler.utils.Constant
import kotlinx.android.synthetic.main.custom_toolbar.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_main)
class MainActivity : BaseActivity<MainContract.MainView, MainPresenterImpl>(),
    MainContract.MainView {

    private var dashboardFragment = DashboardFragment_()
    private var searchFragment = SearchFragment_()
    private var moreFragment = MoreFragment_()
    private var index: Int = -1
    private var mPublicTrips: ArrayList<PublicTrip> = ArrayList()


    override fun initPresenter() {
        mPresenter = MainPresenterImpl(this)
    }

    override fun afterViews() {
        initToolbar()
        initBottomNavigationView()
        showLoading()
        getPublicTrips()
        setFragment(dashboardFragment, Constant.INDEX_FRAGMENT_DASBOARD)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                Constant.REQUEST_CODE_CREATE_PUBLIC_TRIP -> {
                    getPublicTrips()
                }
                Constant.REQUEST_CODE_UPDATE_PUBLIC_TRIP -> {
                    getPublicTrips()
                }
                Constant.REQUEST_CODE_UPDATE_USER_AVATAR -> {
                    moreFragment.setupViews()
                }
            }
        }
    }

    /**
     * function to handle result after get public trips successfully
     * */

    override fun getPublicTripsResult(publicTrips: ArrayList<PublicTrip>?) {
        if (publicTrips != null) {
            if (publicTrips.size <= 0) {
                if (tv_no_upcoming_dashboard != null)
                    tv_no_upcoming_dashboard.visibility = View.VISIBLE
                if (rcv_public_trip_dashboard != null)
                    rcv_public_trip_dashboard.visibility = View.GONE
            } else {
                if (tv_no_upcoming_dashboard != null)
                    tv_no_upcoming_dashboard.visibility = View.GONE
                if (rcv_public_trip_dashboard != null)
                    rcv_public_trip_dashboard.visibility = View.VISIBLE
                mPublicTrips.clear()
                mPublicTrips.addAll(publicTrips)
            }
        }
//        setFragment(dashboardFragment, Constant.INDEX_FRAGMENT_DASBOARD)
        dismissLoading()
        dashboardFragment.dismissSwipeRefreshLayout()
        dashboardFragment.notifyDataSetChanged(mPublicTrips)
    }

    override fun deletePublicTripResult() {
        getPublicTrips()
    }

    fun initToolbar() {
        setSupportActionBar(toolbar_appbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        imgv_actionbar_back.visibility = View.GONE
        tv_actionbar_title.text = getString(R.string.dashboard)

    }

    fun initBottomNavigationView() {
        bottom_navigation_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_dashboard -> {
//                    getPublicTrips()
                    setFragment(dashboardFragment, Constant.INDEX_FRAGMENT_DASBOARD)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_search -> {
                    setFragment(searchFragment, Constant.INDEX_FRAGMENT_SEARCH)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_more -> {
                    setFragment(moreFragment, Constant.INDEX_FRAGMENT_MORE)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }
    }

    fun setFragment(fragment: Fragment, i: Int) {
//        if (i == index) return
        when (fragment) {
            is DashboardFragment -> {
                val b = Bundle()
                b.putParcelableArrayList(Constant.PUBLIC_TRIPS, mPublicTrips)
                dashboardFragment.arguments = b
                index = Constant.INDEX_FRAGMENT_DASBOARD
                tv_actionbar_title.setText(getString(R.string.dashboard))
            }
            is SearchFragment -> {
                index = Constant.INDEX_FRAGMENT_SEARCH
                tv_actionbar_title.setText(getString(R.string.search))
            }
            is MoreFragment -> {
                index = Constant.INDEX_FRAGMENT_MORE
                tv_actionbar_title.setText(getString(R.string.more))
            }
        }
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_container, fragment, "fragment" + i)
            .commit()
    }

    fun getPublicTrips() {
        showLoading()
        mPresenter!!.getPublicTrips()
    }

    fun deletePublicTrip(id: Int) {
        showLoading()
        mPresenter!!.deletePublicTrip(id)
    }

    fun showConfirmDialog(id: Int) {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle(R.string.confirm_delete_trip)
            .setPositiveButton(R.string.yes, { dialogInterface, i -> deletePublicTrip(id) })
            .setNegativeButton(R.string.no, null)
            .create()
        alertDialog.getWindow()!!.setBackgroundDrawableResource(R.drawable.background_dialog)
        alertDialog.show()
    }
}
