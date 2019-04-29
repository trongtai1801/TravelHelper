package dut.t2.travelhepler.ui.main.more.profile.references

import android.app.Activity
import android.os.Bundle
import android.view.View
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhepler.R
import org.androidannotations.annotations.EActivity
import android.widget.RadioButton
import com.google.gson.JsonObject
import dut.t2.travelhepler.service.model.Reference
import dut.t2.travelhepler.utils.Constant
import kotlinx.android.synthetic.main.activity_write_reference.*
import org.androidannotations.annotations.Click

@EActivity(R.layout.activity_write_reference)
class WriteReferenceActivity : BaseActivity<WriteReferenceContract.WriteReferenceView, WriteReferencePresenterImpl>(),
    WriteReferenceContract.WriteReferenceView {

    companion object {
        var sStatus = true
        var sUserId = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sUserId = intent.getStringExtra(Constant.USER_ID)
    }

    override fun initPresenter() {
        mPresenter = WriteReferencePresenterImpl(this)
    }

    override fun afterViews() {

    }

    @Click(R.id.btn_submit_reference)
    fun onClick(v: View) {
        when (v.id) {
            R.id.btn_submit_reference ->
                if (edt_your_references.text.equals("")) {
                    showToast(getString(R.string.please_input_reference))
                    return
                } else submit()
        }
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked
            when (view.getId()) {
                R.id.radio_yes ->
                    if (checked) {
                        sStatus = true
                    }
                R.id.radio_no ->
                    if (checked) {
                        sStatus = false
                    }
            }
        }
    }

    override fun writeReferenceResult(reference: Reference) {
        if (reference != null) {
            setResult(Activity.RESULT_OK)
            finish()
        }
        dismissLoading()
    }

    fun submit() {
        var reference = JsonObject()
        reference.addProperty("Content", edt_your_references.text.toString())
        reference.addProperty("Status", sStatus)

        showLoading()
        mPresenter!!.writeReference(sUserId, reference)
    }
}