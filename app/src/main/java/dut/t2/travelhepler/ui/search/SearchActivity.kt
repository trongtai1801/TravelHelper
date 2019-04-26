package dut.t2.travelhepler.ui.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhepler.R
import dut.t2.travelhepler.utils.Constant
import kotlinx.android.synthetic.main.activity_search.*
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_search)
class SearchActivity : BaseActivity<SearchContract.SearchView, SearchPresenterImpl>(),
    SearchContract.SearchView {

    var mHostSuggestions: ArrayList<HostSuggestion> = ArrayList()

    override fun initPresenter() {
        mPresenter = SearchPresenterImpl(this)
    }

    override fun afterViews() {
        setupSearchView()
        showLoading()
        mPresenter!!.getSuggestAddress()
    }

    override fun getSuggestAddressResult(addresses: ArrayList<String>) {
        dismissLoading()
        mHostSuggestions.clear()
        for (addr: String in addresses) mHostSuggestions.add(HostSuggestion(addr))
    }

    fun setupSearchView() {
        //listen text changed
        floating_search_view.setOnQueryChangeListener(object : FloatingSearchView.OnQueryChangeListener {
            @Override
            override fun onSearchTextChanged(oldQuery: String?, newQuery: String?) {
                if (!oldQuery.equals("") && newQuery.equals("")) {
                    floating_search_view.clearSuggestions()
                } else {
                    floating_search_view.showProgress()
                    floating_search_view.swapSuggestions(getHostSuggestions(newQuery!!))
                    floating_search_view.hideProgress()
                }
            }
        })

        floating_search_view.setOnFocusChangeListener(object : FloatingSearchView.OnFocusChangeListener {
            override fun onFocus() {
                floating_search_view.showProgress()
                floating_search_view.swapSuggestions(getHostSuggestions(floating_search_view.query!!))
                floating_search_view.hideProgress()
            }

            override fun onFocusCleared() {

            }
        })

        floating_search_view.setOnSearchListener(object : FloatingSearchView.OnSearchListener {
            override fun onSuggestionClicked(searchSuggestion: SearchSuggestion?) {
                var hostSuggestion = searchSuggestion as HostSuggestion
                setResult(Activity.RESULT_OK, Intent().putExtra(Constant.SEARCH_HOST_STRING, hostSuggestion.mHostName))
                finish()
            }

            override fun onSearchAction(currentQuery: String?) {
                setResult(
                    Activity.RESULT_OK,
                    Intent().putExtra(Constant.SEARCH_HOST_STRING, currentQuery)
                )
                finish()
            }
        })

        //back press
        floating_search_view.setOnHomeActionClickListener({ onBackPressed() })
    }

    fun getHostSuggestions(query: String): ArrayList<HostSuggestion> {
        var hostSuggestions: ArrayList<HostSuggestion> = ArrayList()
        for (hostSuggestion: HostSuggestion in mHostSuggestions) {
            if (hostSuggestion.mHostName.toLowerCase().contains(query.toLowerCase()))
                hostSuggestions.add(hostSuggestion)
        }
        return hostSuggestions
    }
}