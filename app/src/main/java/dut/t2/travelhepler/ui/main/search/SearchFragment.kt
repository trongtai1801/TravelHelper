package dut.t2.travelhepler.ui.main.search

import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import dut.t2.travelhepler.R
import dut.t2.travelhepler.ui.search.SearchActivity_
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EFragment

@EFragment(R.layout.fragment_search)
class SearchFragment : Fragment(){

    @AfterViews
    fun afterViews() {
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        activity!!.menuInflater.inflate(R.menu.menu_search, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.item_search -> {
                SearchActivity_.intent(this).start()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}