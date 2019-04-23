package dut.t2.travelhepler.ui.profile.update

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import com.google.gson.JsonObject
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhepler.R
import dut.t2.travelhepler.ui.trips.create.CreateTripActivity
import dut.t2.travelhepler.utils.CalendarUtils
import dut.t2.travelhepler.utils.Constant
import dut.t2.travelhepler.utils.SessionManager
import kotlinx.android.synthetic.main.activity_update_profile.*
import kotlinx.android.synthetic.main.custom_appbar_layout.toolbar_appbar
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EActivity
import java.text.SimpleDateFormat
import java.util.*

@EActivity(R.layout.activity_update_profile)
class UpdateProfileActivity : BaseActivity<UpdateProfileContract.UpdateProfileView, UpdateProfilePresenterImpl>(),
    UpdateProfileContract.UpdateProfileView {
    override fun initPresenter() {
        mPresenter = UpdateProfilePresenterImpl(this)
    }

    override fun afterViews() {
        initToolbar()
        setupViews()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_update, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.item_update -> {
                showToast("Update")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @Click(R.id.edt_content_birthday_profile, R.id.edt_content_gender_profile)
    fun onClick(v: View) {
        when (v.id) {
            R.id.edt_content_gender_profile -> {
                showGenderDialog()
            }
            R.id.edt_content_birthday_profile -> {
                showCalendar(edt_content_birthday_profile)
            }
        }
    }

    fun initToolbar() {
        setSupportActionBar(toolbar_appbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        title = SessionManager.Profile!!.fullName
        toolbar_appbar.setNavigationOnClickListener { view -> onBackPressed() }
    }

    fun setupViews() {
        edt_content_full_name_profile.setText(SessionManager.Profile!!.fullName)
        edt_content_address_profile.setText(SessionManager.Profile?.address)
        edt_content_birthday_profile.setText(CalendarUtils.convertStringFormat(SessionManager.Profile?.splitBirthday()!!))
        if (SessionManager.Profile!!.gender) {
            edt_content_gender_profile.setText(getString(R.string.male))
        } else
            edt_content_gender_profile.setText(getString(R.string.female))
        edt_content_occupation_profile.setText(SessionManager.Profile?.occupation)
        edt_content_fluence_profile.setText(SessionManager.Profile?.fluentLanguage)
        edt_content_learning_profile.setText(SessionManager.Profile?.learningLanguage)
        edt_content_about_me_profile.setText(SessionManager.Profile?.about)
        edt_content_interest_profile.setText(SessionManager.Profile?.interest)
    }

    fun showGenderDialog() {
        val itemList = ArrayList<DialogIOSSheet.SheetItem>()
        val male = DialogIOSSheet.SheetItem(resources.getString(R.string.male))
        val female = DialogIOSSheet.SheetItem(resources.getString(R.string.female))
        itemList.clear()
        itemList.add(male)
        itemList.add(female)
        DialogIOSSheet.Builder(this)
            .setData(itemList, DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    0 -> edt_content_gender_profile.setText(resources.getString(R.string.male))
                    else -> edt_content_gender_profile.setText(resources.getString(R.string.female))
                }
            })
            .show()
    }

    fun showCalendar(v: EditText) {
        val calendar = Calendar.getInstance()

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val sdf = SimpleDateFormat(Constant.DATE_FORMAT_SEND, Locale.US)
            v.setText(sdf.format(calendar.time))
        }

        var dp = DatePickerDialog(this@UpdateProfileActivity, dateSetListener, year, month, day)
        dp.datePicker.maxDate = calendar.getTimeInMillis()
        dp.show()
    }

    fun CreateJsonObject(): JsonObject {
        var jsonObject = JsonObject()
        jsonObject.addProperty("FullName", edt_content_full_name_profile.text.toString())
        jsonObject.addProperty("Gender", edt_content_gender_profile.text.toString())
        jsonObject.addProperty("Birthday", edt_content_birthday_profile.text.toString())
        jsonObject.addProperty("Occupation", edt_content_occupation_profile.text.toString())
        jsonObject.addProperty("FluentLanguage", edt_content_fluence_profile.text.toString())
        jsonObject.addProperty("LearningLanguage", edt_content_learning_profile.text.toString())
        jsonObject.addProperty("About", edt_content_about_me_profile.text.toString())
        jsonObject.addProperty("Interest", edt_content_interest_profile.text.toString())
        return jsonObject
    }

}