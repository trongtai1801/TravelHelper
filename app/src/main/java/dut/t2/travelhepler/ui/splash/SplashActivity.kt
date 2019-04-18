package dut.t2.travelhepler.ui.splash

import android.os.Handler
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhepler.R
import dut.t2.travelhepler.ui.login.LoginActivity_
import dut.t2.travelhepler.ui.main.MainActivity_
import dut.t2.travelhepler.utils.Constant
import dut.t2.travelhepler.utils.RealmDAO
import dut.t2.travelhepler.utils.SharedPrefs
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_splash)
class SplashActivity : BaseActivity<SplashContract.SplashView, SplashPresenterImpl>(), SplashContract.SplashView {

    override fun initPresenter() {
        mPresenter = SplashPresenterImpl(this)
    }

    override fun afterViews() {
        mActionBar!!.hide()

        Handler().postDelayed({
            if (SharedPrefs.getInstance().get(Constant.ACCESS_TOKEN, String::class.java) == null ||
                SharedPrefs.getInstance().get(Constant.ACCESS_TOKEN, String::class.java).equals("")
            )
                RealmDAO.deleteProfileLogin()

            if (RealmDAO.getProfileLogin() != null) MainActivity_.intent(this).start()
            else LoginActivity_.intent(this).start()
            finish()
        }, 1500)


    }
}