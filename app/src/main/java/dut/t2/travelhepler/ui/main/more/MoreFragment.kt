package dut.t2.travelhepler.ui.main.more

import android.support.v4.app.Fragment
import android.view.View
import dut.t2.travelhepler.R
import dut.t2.travelhepler.ui.splash.SplashActivity_
import dut.t2.travelhepler.utils.Constant
import dut.t2.travelhepler.utils.SharedPrefs
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EFragment

@EFragment(R.layout.fragment_more)
class MoreFragment : Fragment() {
    @Click(R.id.tv_logout)
    fun onClick(v: View) {
        when (v.id) {
            R.id.tv_logout -> {
                SharedPrefs.getInstance().put(Constant.ACCESS_TOKEN, "");
                SplashActivity_.intent(activity).start()
                activity!!.finish()
            }
        }
    }
}