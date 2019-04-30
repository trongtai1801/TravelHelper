package dut.t2.travelhepler.ui.main.search

import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import dut.t2.travelhelper.service.model.Profile
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.PublicTrip
import dut.t2.travelhepler.ui.hosts.HostsActivity_
import dut.t2.travelhepler.ui.main.MainActivity
import dut.t2.travelhepler.ui.main.search.search.SearchActivity_
import dut.t2.travelhepler.ui.travelers.TravelersActivity_
import dut.t2.travelhepler.utils.Constant
import kotlinx.android.synthetic.main.fragment_search.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EFragment

@EFragment(R.layout.fragment_search)
class SearchFragment : Fragment() {

    var mHosts: ArrayList<Profile> = ArrayList()
    var mTravelers: ArrayList<PublicTrip> = ArrayList()

    @AfterViews
    fun afterViews() {
        setHasOptionsMenu(true)
        showHideHostCardView()
        showHideTravelersCardView()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        activity!!.menuInflater.inflate(R.menu.menu_search, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.item_search -> {
                SearchActivity_.intent(context).startForResult(Constant.REQUEST_CODE_GET_SEARCH_HOST_STRING)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @Click(R.id.cv_host, R.id.cv_traveler)
    fun onClick(v: View) {
        when (v.id) {
            R.id.cv_host -> {
                HostsActivity_.intent(context).extra(Constant.HOSTS, mHosts).start()
            }
            R.id.cv_traveler -> {
                TravelersActivity_.intent(this).extra(Constant.TRAVELERS, mTravelers).start()
            }
        }
    }

    override fun onDestroyView() {
        mHosts.clear()
        showHideHostCardView()
        mTravelers.clear()
        showHideTravelersCardView()
        super.onDestroyView()
    }

    public fun setHosts(hosts: ArrayList<Profile>) {
        mHosts.clear()
        mHosts.addAll(hosts)
        showHideHostCardView()
        tv_total_host_item_search.text = hosts.size.toString() + " " + context!!.getString(R.string.host)
        if (hosts.size > 0) {
            img_first_host.visibility = View.VISIBLE
            Glide.with(context!!).load(hosts.get(0).avatar)
                .placeholder(context!!.getDrawable(R.drawable.ic_user_circle))
                .into(img_first_host)
        } else img_first_host.visibility = View.INVISIBLE
        if (hosts.size > 1) {
            img_second_host.visibility = View.VISIBLE
            Glide.with(context!!).load(hosts.get(1).avatar)
                .placeholder(context!!.getDrawable(R.drawable.ic_user_circle))
                .into(img_second_host)
        } else img_second_host.visibility = View.INVISIBLE
        if (hosts.size > 2) {
            img_third_host.visibility = View.VISIBLE
            Glide.with(context!!).load(hosts.get(2).avatar)
                .placeholder(context!!.getDrawable(R.drawable.ic_user_circle))
                .into(img_third_host)
        } else img_third_host.visibility = View.INVISIBLE
    }

    public fun setTravelers(travelers: ArrayList<PublicTrip>) {
        mTravelers.clear()
        mTravelers.addAll(travelers)
        showHideTravelersCardView()
        tv_total_traveler_item_search.text = travelers.size.toString() + " " + getString(R.string.travelers)
        if (travelers.size > 0) {
            img_first_traveler.visibility = View.VISIBLE
            Glide.with(context!!).load(travelers.get(0).user!!.avatar)
                .placeholder(context!!.getDrawable(R.drawable.ic_user_circle))
                .into(img_first_traveler)
        } else img_first_traveler.visibility = View.INVISIBLE
        if (travelers.size > 1) {
            img_second_traveler.visibility = View.VISIBLE
            Glide.with(context!!).load(travelers.get(1).user!!.avatar)
                .placeholder(context!!.getDrawable(R.drawable.ic_user_circle))
                .into(img_second_traveler)
        } else img_second_traveler.visibility = View.INVISIBLE
        if (travelers.size > 2) {
            img_third_traveler.visibility = View.VISIBLE
            Glide.with(context!!).load(travelers.get(2).user!!.avatar)
                .placeholder(context!!.getDrawable(R.drawable.ic_user_circle))
                .into(img_third_traveler)
        } else img_third_traveler.visibility = View.INVISIBLE
    }

    fun showHideHostCardView() {
        if (mHosts.size <= 0) {
            cv_host.visibility = View.GONE
        } else cv_host.visibility = View.VISIBLE
    }

    fun showHideTravelersCardView() {
        if (mTravelers.size <= 0) {
            cv_traveler.visibility = View.GONE
        } else cv_traveler.visibility = View.VISIBLE
    }
}