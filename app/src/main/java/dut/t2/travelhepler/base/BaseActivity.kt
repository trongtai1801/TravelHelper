package dut.t2.travelhelper.base

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import dut.t2.travelhepler.R
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EActivity
import java.lang.Exception

@EActivity
abstract class BaseActivity<V : BaseView, T : BasePresenter<V>> : AppCompatActivity(), BaseView {

    private val TAG = this.javaClass.simpleName
    protected var mPresenter: T? = null

    lateinit var mProgressDialog: ProgressDialog

    protected abstract fun initPresenter()
    protected abstract fun afterViews()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.initPresenter()
        if (mPresenter != null) mPresenter!!.attachView(this as V)
        initProgressDialog()
    }

    @AfterViews
    protected fun initViews() {
        this.afterViews()
    }

    override fun onDestroy() {
        mPresenter!!.detachView()
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing) {
                mProgressDialog.dismiss()
            }
        } catch (e: Exception) {
            Log.e(TAG, "" + e.message)
        }
        super.onDestroy()
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun initProgressDialog() {
        mProgressDialog = ProgressDialog(this)
        mProgressDialog.setMessage(getString(R.string.loading))
        mProgressDialog.setCancelable(false)
    }

    override fun showLoading() {
        if (mProgressDialog != null && !mProgressDialog.isShowing && !isFinishing) mProgressDialog.show()
    }

    override fun dismissLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing && !isFinishing) mProgressDialog.dismiss()
    }

    override fun showError(error: String) {
        showAlertDialog(error)
    }

    override fun showMessage(message: String) {
        showAlertDialog(message)
    }

    protected fun showAlertDialog(msg: String) {
        try {
            AlertDialog.Builder(this)
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok, null)
                .show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}