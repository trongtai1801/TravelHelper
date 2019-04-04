package dut.t2.travelhepler.ui.main

import dut.t2.basemvp.base.BaseActivity
import dut.t2.travelhepler.R
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_main)
class MainActivity : BaseActivity<MainContract.MainView, MainPresenterImpl>(), MainContract.MainView {

    override var mPresenter: MainPresenterImpl = MainPresenterImpl()

    override fun afterViews() {

    }
}
