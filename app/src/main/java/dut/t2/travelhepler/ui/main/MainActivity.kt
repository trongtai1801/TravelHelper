package dut.t2.travelhepler.ui.main

import dut.t2.basemvp.base.BaseActivity
import dut.t2.travelhepler.R
import dut.t2.travelhepler.utils.Constant
import dut.t2.travelhepler.utils.SharedPrefs
import kotlinx.android.synthetic.main.actionbar.*
import kotlinx.android.synthetic.main.activity_main.*
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_main)
class MainActivity : BaseActivity<MainContract.MainView, MainPresenterImpl>(), MainContract.MainView {

    override var mPresenter: MainPresenterImpl = MainPresenterImpl()

    override fun afterViews() {
        tv_actionbar_title.setText(getString(R.string.dashboard))
        mActionBar!!.setDisplayHomeAsUpEnabled(false)
        tv_main.setText(SharedPrefs.getInstance().get(Constant.ACCESS_TOKEN, String::class.java))
    }
}
