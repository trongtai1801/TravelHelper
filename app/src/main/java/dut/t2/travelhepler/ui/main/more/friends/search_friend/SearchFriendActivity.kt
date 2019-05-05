package dut.t2.travelhepler.ui.main.more.friends.search_friend

import android.app.Activity
import android.content.Intent
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhepler.R
import dut.t2.travelhepler.ui.main.search.search.HostSuggestion
import dut.t2.travelhepler.utils.Constant
import kotlinx.android.synthetic.main.activity_search.*
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_search)
class SearchFriendActivity : BaseActivity<SearchFriendContract.SearchFriendView, SearchFriendPresenterImpl>(),
    SearchFriendContract.SearchFriendView {
    override fun initPresenter() {
        mPresenter = SearchFriendPresenterImpl(this)
    }

    override fun afterViews() {
        setupSearchView()
    }

    fun setupSearchView() {
        floating_search_view.setOnSearchListener(object : FloatingSearchView.OnSearchListener {
            override fun onSuggestionClicked(searchSuggestion: SearchSuggestion?) {

            }

            override fun onSearchAction(currentQuery: String?) {
                setResult(
                    Activity.RESULT_OK,
                    Intent().putExtra(Constant.SEARCH_USER_STRING, currentQuery)
                )
                finish()
            }
        })

        //back press
        floating_search_view.setOnHomeActionClickListener { onBackPressed() }
    }
}