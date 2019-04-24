package dut.t2.travelhepler.ui.profile.references

import android.view.View
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhepler.R
import kotlinx.android.synthetic.main.custom_appbar_layout_dark.*
import kotlinx.android.synthetic.main.custom_appbar_layout_light.*
import kotlinx.android.synthetic.main.custom_appbar_layout_light.img_back_appbar
import kotlinx.android.synthetic.main.custom_appbar_layout_light.tv_title_appbar
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_references)
class ReferencesActivity : BaseActivity<ReferencesContract.ReferencesView, ReferencesPresenterImpl>(),
    ReferencesContract.ReferencesView {
    override fun initPresenter() {
        mPresenter = ReferencesPresenterImpl(this)
    }

    override fun afterViews() {
        initToolbar()
    }

    fun initToolbar() {
        setSupportActionBar(toolbar_appbar_dark)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        tv_title_appbar.visibility = View.VISIBLE
        img_back_appbar.visibility = View.VISIBLE
        tv_title_appbar.text = getString(R.string.reference)
        img_back_appbar.setOnClickListener { onBackPressed() }
    }
}