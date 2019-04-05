package dut.t2.travelhepler.ui.main

import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhepler.R
import kotlinx.android.synthetic.main.actionbar.*
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_main)
class MainActivity : BaseActivity<MainContract.MainView, MainPresenterImpl>(), MainContract.MainView {

    override var mPresenter: MainPresenterImpl = MainPresenterImpl()

    override fun afterViews() {
        tv_actionbar_title.setText(getString(R.string.dashboard))
        mActionBar!!.setDisplayHomeAsUpEnabled(false)
    }
}
