package dut.t2.travelhepler.ui.login

import android.view.View
import dut.t2.basemvp.base.BaseActivity
import dut.t2.travelhelper.service.model.User
import dut.t2.travelhepler.R
import dut.t2.travelhepler.ui.main.MainActivity_
import kotlinx.android.synthetic.main.activity_login.*
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_login)
class LoginActivity : BaseActivity<LoginContract.LoginView, LoginPresenterImpl>(),
    LoginContract.LoginView {

    override var mPresenter: LoginPresenterImpl = LoginPresenterImpl()

    override fun afterViews() {
        mActionBar!!.hide()
    }

    @Click(R.id.tv_login_sign_up, R.id.btn_login_submit)
    fun onClick(v: View) {
        when (v.id) {
            R.id.tv_login_sign_up -> {
//                SignUpActivity_.intent(this).start()
            }
            R.id.btn_login_submit -> {
                if (!isEmptyField()) {
                    showLoading()
                    var userName: String = edt_login_user_name.text.toString()
                    var password: String = edt_login_password.text.toString();
                    mPresenter.login(User.builder().userName(userName).password(password).build())
                } else showToast(getString(R.string.empty_field))
            }
        }
    }

    override fun loginResult() {
        dismissLoading()
        MainActivity_.intent(this).start()
        finish()
    }

    fun isEmptyField(): Boolean {
        if (edt_login_user_name.text.isEmpty() || edt_login_password.text.isEmpty()) return true;
        return false
    }
}