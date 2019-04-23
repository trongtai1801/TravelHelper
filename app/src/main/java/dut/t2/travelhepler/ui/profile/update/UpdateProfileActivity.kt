package dut.t2.travelhepler.ui.profile.update

import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhepler.R
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_update_profile)
class UpdateProfileActivity : BaseActivity<UpdateProfileContract.UpdateProfileView, UpdateProfilePresenterImpl>(),
    UpdateProfileContract.UpdateProfileView {
    override fun initPresenter() {

    }

    override fun afterViews() {
    }
}