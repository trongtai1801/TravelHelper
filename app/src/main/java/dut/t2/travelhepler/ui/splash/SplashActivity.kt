package dut.t2.travelhepler.ui.splash

import android.os.Handler
import dut.t2.basemvp.base.BaseActivity
import dut.t2.travelhepler.R
import dut.t2.travelhepler.ui.main.MainActivity_
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_splash)
class SplashActivity : BaseActivity<SplashContract.SplashView, SplashPresenterImpl>(), SplashContract.SplashView {
    override var mPresenter: SplashPresenterImpl = SplashPresenterImpl()

    override fun afterViews() {
        mActionBar!!.hide()

        Handler().postDelayed({
//            LoginActivity_.intent(this).start()
            MainActivity_.intent(this).start()
            finish()
        }, 2500)
    }
}