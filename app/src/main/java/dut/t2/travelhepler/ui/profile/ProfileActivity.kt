package dut.t2.travelhepler.ui.profile

import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhepler.R
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_profile)
class ProfileActivity : BaseActivity<ProfileContract.ProfileView, ProfilePresenterImpl>(),
    ProfileContract.ProfileView {
    override fun initPresenter() {
        mPresenter = ProfilePresenterImpl(this)
    }

    override fun afterViews() {
        mActionBar!!.hide()
    }
}