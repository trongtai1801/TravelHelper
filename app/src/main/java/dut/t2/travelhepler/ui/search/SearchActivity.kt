package dut.t2.travelhepler.ui.search

import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhepler.R
import org.androidannotations.annotations.EActivity


@EActivity(R.layout.activity_search)
class SearchActivity : BaseActivity<SearchContract.SearchView, SearchPresenterImpl>(),
    SearchContract.SearchView {
    override fun initPresenter() {
        mPresenter = SearchPresenterImpl(this)
    }

    override fun afterViews() {

    }
}