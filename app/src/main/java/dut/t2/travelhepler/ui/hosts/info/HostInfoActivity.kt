package dut.t2.travelhepler.ui.hosts.info

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhelper.service.model.Profile
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.Home
import dut.t2.travelhepler.ui.profile.home.HomeActivity_
import dut.t2.travelhepler.ui.profile.photos.PhotosActivity_
import dut.t2.travelhepler.ui.profile.references.ReferencesActivity_
import dut.t2.travelhepler.utils.*
import kotlinx.android.synthetic.main.activity_profile.*
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_host_info)
class HostInfoActivity : BaseActivity<HostInfoContract.HostInfoView, HostInfoPresenterImpl>(),
    HostInfoContract.HostInfoView {

    private var host: Profile? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        host = intent.getParcelableExtra(Constant.HOST)
    }

    override fun initPresenter() {
        mPresenter = HostInfoPresenterImpl(this)
    }

    override fun afterViews() {
        initToolbar()
        setupViews()
    }

    @Click(
        R.id.tv_lb_photos_profile, R.id.tv_lb_reference_profile,
        R.id.tv_lb_home_profile, R.id.btn_request_stay_host
    )
    fun onClick(v: View) {
        when (v.id) {
            R.id.tv_lb_photos_profile -> {
                PhotosActivity_.intent(this).start()
            }
            R.id.tv_lb_reference_profile -> {
                ReferencesActivity_.intent(this).start()
            }
            R.id.tv_lb_home_profile -> {
                showLoading()
                mPresenter!!.getHomeInfo(host!!.id)
            }
            R.id.btn_request_stay_host -> {

            }
        }
    }

    override fun getHomeInfoResult(home: Home) {
        if (home != null) HomeActivity_.intent(this).extra(Constant.HOME, home).start()
        else showToast(getString(R.string.dont_have_home))
        dismissLoading()
    }

    fun initToolbar() {
        setSupportActionBar(toolbar_show_profile)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        title = host!!.fullName
        Glide.with(this).load(host!!.avatar)
            .placeholder(this.getDrawable(R.drawable.ic_user_circle))
            .into(img_avatar_toolbar)
        toolbar_show_profile.setNavigationOnClickListener { view -> onBackPressed() }
    }

    fun setupViews() {
        tv_content_address_profile.text = host!!.address
        if (!host!!.splitBirthday()!!.equals(""))
            tv_content_birthday_profile.text =
                CalendarUtils.convertStringFormat(host!!.splitBirthday()!!)
        else tv_content_birthday_profile.text = ""
        if (host!!.gender) {
            tv_content_gender_profile.text = getString(R.string.male)
        } else
            tv_content_gender_profile.text = getString(R.string.female)
        tv_content_occupation_profile.text = host!!.address
        tv_content_fluence_profile.text = host!!.fluentLanguage
        tv_content_learning_profile.text = host!!.learningLanguage
        tv_content_about_me_profile.text = host!!.about
        tv_content_interest_profile.text = host!!.interest
    }
}