package dut.t2.travelhepler.ui.main

import android.support.v4.app.Fragment
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhepler.R
import dut.t2.travelhepler.ui.main.dashboard.DashboardFragment
import dut.t2.travelhepler.ui.main.dashboard.DashboardFragment_
import dut.t2.travelhepler.ui.main.more.MoreFragment
import dut.t2.travelhepler.ui.main.more.MoreFragment_
import dut.t2.travelhepler.ui.main.search.SearchFragment
import dut.t2.travelhepler.ui.main.search.SearchFragment_
import dut.t2.travelhepler.utils.Constant
import kotlinx.android.synthetic.main.actionbar.*
import kotlinx.android.synthetic.main.activity_main.*
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_main)
class MainActivity : BaseActivity<MainContract.MainView, MainPresenterImpl>(),
    MainContract.MainView {

    override var mPresenter: MainPresenterImpl = MainPresenterImpl()
    private var dashboardFragment = DashboardFragment_()
    private var searchFragment = SearchFragment_()
    private var moreFragment = MoreFragment_()
    private var index: Int = -1

    override fun afterViews() {
        tv_actionbar_title.setText(getString(R.string.dashboard))
        mActionBar!!.setDisplayHomeAsUpEnabled(false)
        initBottomNavigationView()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    fun initBottomNavigationView() {
        setFragment(dashboardFragment, Constant.INDEX_FRAGMENT_DASBOARD)
        bottom_navigation_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_dashboard -> {
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
        if (i == index) return
        when (fragment) {
            is DashboardFragment -> index = Constant.INDEX_FRAGMENT_DASBOARD
            is SearchFragment -> index = Constant.INDEX_FRAGMENT_SEARCH
            is MoreFragment -> index = Constant.INDEX_FRAGMENT_MORE
        }
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_container, fragment, "fragment" + i)
            .commit()
    }
}
