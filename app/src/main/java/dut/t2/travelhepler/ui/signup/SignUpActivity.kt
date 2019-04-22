package dut.t2.travelhelper.ui.signup

import android.view.View
import android.widget.TextView
import dut.t2.travelhelper.base.BaseActivity
import kotlinx.android.synthetic.main.actionbar.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.TextChange
import android.util.Patterns
import android.text.TextUtils
import com.google.gson.JsonObject
import dut.t2.travelhepler.R
import org.androidannotations.annotations.Click

@EActivity(R.layout.activity_sign_up)
class SignUpActivity : BaseActivity<SignUpContract.SignUpView, SignUpPresenterImpl>(), SignUpContract.SignUpView {

    override fun initPresenter() {
        mPresenter = SignUpPresenterImpl(this)
    }

    override fun afterViews() {
        initToolbar()
    }

    @TextChange(
        R.id.edt_sign_up_user_name, R.id.edt_sign_up_password, R.id.edt_sign_up_full_name,
        R.id.edt_sign_up_email
    )
    fun onTextChange(tv: TextView, text: CharSequence) {
        when (tv.id) {
            R.id.edt_sign_up_user_name -> {
                if (text.isNotEmpty()) ic_sign_up_user_name.visibility = View.VISIBLE
                else ic_sign_up_user_name.visibility = View.INVISIBLE
            }
            R.id.edt_sign_up_password -> {
                if (text.isNotEmpty() && (text.length >= 6)) ic_sign_up_password.visibility = View.VISIBLE
                else ic_sign_up_password.visibility = View.INVISIBLE
            }
            R.id.edt_sign_up_full_name -> {
                if (text.isNotEmpty()) ic_sign_up_full_name.visibility = View.VISIBLE
                else ic_sign_up_full_name.visibility = View.INVISIBLE
            }
            R.id.edt_sign_up_email -> {
                if (isValidEmail(text)) ic_sign_up_email.visibility = View.VISIBLE
                else ic_sign_up_email.visibility = View.INVISIBLE
            }
        }
    }

    @Click(R.id.btn_sign_up_submit)
    fun onClick(v: View) {
        when (v.id) {
            R.id.btn_sign_up_submit -> {
                if (isUserNameEmpty()) {
                    showToast(getString(R.string.require_user_name))
                    return
                }
                if (isPasswordEmpty()) {
                    showToast(getString(R.string.require_password))
                    return
                }
                var signUpObject = JsonObject()
                signUpObject.addProperty("Username", edt_sign_up_user_name.text.toString())
                signUpObject.addProperty("Password", edt_sign_up_password.text.toString())
                signUpObject.addProperty("Fullname", edt_sign_up_full_name.text.toString())
                signUpObject.addProperty("Email", edt_sign_up_email.text.toString())
                showLoading()
                mPresenter!!.signUp(signUpObject)
            }
        }
    }

    override fun signUpResult() {
        dismissLoading()
        showToast(getString(R.string.register_success))
        finish()
    }

    fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        imgv_actionbar_back.setOnClickListener { finish() }
        tv_actionbar_title.text = getString(R.string.sign_up)

    }

    fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    fun isUserNameEmpty(): Boolean {
        return edt_sign_up_user_name.text.toString().isEmpty()
    }

    fun isPasswordEmpty(): Boolean {
        return edt_sign_up_password.text.toString().isEmpty()
    }
}