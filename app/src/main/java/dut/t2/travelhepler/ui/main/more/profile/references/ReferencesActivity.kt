package dut.t2.travelhepler.ui.main.more.profile.references

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.Reference
import dut.t2.travelhepler.utils.Constant
import dut.t2.travelhepler.utils.RealmDAO
import kotlinx.android.synthetic.main.activity_references.*
import kotlinx.android.synthetic.main.custom_appbar_layout_dark.*
import kotlinx.android.synthetic.main.custom_appbar_layout_light.img_back_appbar
import kotlinx.android.synthetic.main.custom_appbar_layout_light.tv_title_appbar
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_references)
class ReferencesActivity : BaseActivity<ReferencesContract.ReferencesView, ReferencesPresenterImpl>(),
    ReferencesContract.ReferencesView {

    private var mReferences = ArrayList<Reference>()
    private var mAdapter: ReferencesAdapter? = null

    companion object {
        var sUserId: String = ""
    }

    override fun initPresenter() {
        mPresenter = ReferencesPresenterImpl(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sUserId = intent.getStringExtra(Constant.USER_ID)
    }

    override fun afterViews() {
        initToolbar()
        initRcv()
        swf_references.setOnRefreshListener {
            mPresenter!!.getReferences(sUserId)
        }
        if (sUserId.equals(RealmDAO.getProfileLogin()!!.id)) fab_write_reference.hide()
        else fab_write_reference.show()
        showLoading()
        mPresenter!!.getReferences(sUserId)
    }

    @Click(R.id.fab_write_reference)
    fun onClick(v: View) {
        when (v.id) {
            R.id.fab_write_reference -> {
                showToast("write")
            }
        }
    }

    override fun getReferencesResult(references: ArrayList<Reference>) {
        if (references != null) {
            if (references.size <= 0) {
                showToast(getString(R.string.dont_have_reference))
                return
            }
            mReferences.clear()
            mReferences.addAll(references)
            mAdapter!!.notifyDataSetChanged()
        } else showMessage(getString(R.string.data_null))
        dismissLoading()
        if (swf_references.isRefreshing) swf_references.isRefreshing = false
    }

    fun initToolbar() {
        setSupportActionBar(toolbar_appbar_dark)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        tv_title_appbar.visibility = View.VISIBLE
        img_back_appbar.visibility = View.VISIBLE
        tv_title_appbar.text = getString(R.string.references)
        img_back_appbar.setOnClickListener { onBackPressed() }
    }

    fun initRcv() {
        mReferences.clear()
        rcv_references.setHasFixedSize(true)
        mAdapter = ReferencesAdapter(this, mReferences, object : ReferencesAdapter.ReferencesClickListener {
            override fun onClick(reference: Reference) {

            }
        })
        rcv_references.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcv_references.adapter = mAdapter
    }
}