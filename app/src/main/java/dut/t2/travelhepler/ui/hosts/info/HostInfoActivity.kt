package dut.t2.travelhepler.ui.hosts.info

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhelper.service.model.Profile
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.Home
import dut.t2.travelhepler.ui.hosts.request.RequestToStayActivity_
import dut.t2.travelhepler.ui.main.more.profile.home.HomeActivity_
import dut.t2.travelhepler.ui.main.more.profile.photos.PhotosActivity_
import dut.t2.travelhepler.ui.main.more.profile.references.ReferencesActivity_
import dut.t2.travelhepler.utils.*
import kotlinx.android.synthetic.main.activity_host_info.*
import kotlinx.android.synthetic.main.activity_profile.img_avatar_toolbar
import kotlinx.android.synthetic.main.activity_profile.toolbar_show_profile
import kotlinx.android.synthetic.main.activity_profile.tv_content_about_me_profile
import kotlinx.android.synthetic.main.activity_profile.tv_content_address_profile
import kotlinx.android.synthetic.main.activity_profile.tv_content_birthday_profile
import kotlinx.android.synthetic.main.activity_profile.tv_content_fluence_profile
import kotlinx.android.synthetic.main.activity_profile.tv_content_gender_profile
import kotlinx.android.synthetic.main.activity_profile.tv_content_interest_profile
import kotlinx.android.synthetic.main.activity_profile.tv_content_learning_profile
import kotlinx.android.synthetic.main.activity_profile.tv_content_occupation_profile
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_host_info)
class HostInfoActivity : BaseActivity<HostInfoContract.HostInfoView, HostInfoPresenterImpl>(),
    HostInfoContract.HostInfoView {

    private var mHost: Profile? = null

    companion object {
        private var sIsFriend = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHost = intent.getParcelableExtra(Constant.HOST)
    }

    override fun initPresenter() {
        mPresenter = HostInfoPresenterImpl(this)
    }

    override fun afterViews() {
        initToolbar()
        checkFriend()
        setupViews()
    }

    override fun onDestroy() {
        sIsFriend = true
        super.onDestroy()
    }

    @Click(
        R.id.tv_lb_photos_profile, R.id.tv_lb_reference_profile,
        R.id.tv_lb_home_profile, R.id.btn_request_stay_host, R.id.btn_add_friend
    )
    fun onClick(v: View) {
        when (v.id) {
            R.id.tv_lb_photos_profile -> {
                PhotosActivity_.intent(this).extra(Constant.USER_ID, mHost!!.id).start()
            }
            R.id.tv_lb_reference_profile -> {
                ReferencesActivity_.intent(this).extra(Constant.USER_ID, mHost!!.id).start()
            }
            R.id.tv_lb_home_profile -> {
                showLoading()
                mPresenter!!.getHomeInfo(mHost!!.id)
            }
            R.id.btn_request_stay_host -> {
                RequestToStayActivity_.intent(this).extra(Constant.HOST, mHost).start()
            }
            R.id.btn_add_friend -> {
                showLoading()
                mPresenter!!.addFriend(mHost!!.id)
            }
        }
    }

    override fun getHomeInfoResult(home: Home) {
        if (home != null) HomeActivity_.intent(this).extra(Constant.HOME, home).start()
        else showToast(getString(R.string.dont_have_home))
        dismissLoading()
    }

    override fun checkFriendResult(isFriend: Boolean) {
        sIsFriend = isFriend
        showHideAddFriendButton()
        dismissLoading()
    }

    override fun addFriendResult() {
        showToast(getString(R.string.add_friend_successfully))
    }

    fun initToolbar() {
        setSupportActionBar(toolbar_show_profile)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        title = mHost!!.fullName
        Glide.with(this).load(mHost!!.avatar)
            .placeholder(this.getDrawable(R.drawable.ic_user_circle))
            .into(img_avatar_toolbar)
        toolbar_show_profile.setNavigationOnClickListener { view -> onBackPressed() }
    }

    fun setupViews() {
        tv_content_address_profile.text = mHost!!.address
        if (mHost!!.id == RealmDAO.getProfileLogin()!!.id) btn_request_stay_host.visibility = View.GONE
        else btn_request_stay_host.visibility = View.VISIBLE
        if (!mHost!!.splitBirthday()!!.equals(""))
            tv_content_birthday_profile.text =
                CalendarUtils.convertStringFormat(mHost!!.splitBirthday()!!)
        else tv_content_birthday_profile.text = ""
        if (mHost!!.gender) {
            tv_content_gender_profile.text = getString(R.string.male)
        } else
            tv_content_gender_profile.text = getString(R.string.female)
        tv_content_occupation_profile.text = mHost!!.address
        tv_content_fluence_profile.text = mHost!!.fluentLanguage
        tv_content_learning_profile.text = mHost!!.learningLanguage
        tv_content_about_me_profile.text = mHost!!.about
        tv_content_interest_profile.text = mHost!!.interest
        showHideAddFriendButton()
    }

    fun checkFriend() {
        showLoading()
        mPresenter!!.checkFriend(mHost!!.id)
    }

    fun showHideAddFriendButton() {
        if (sIsFriend || mHost!!.id == SessionManager.Profile!!.id) rl_add_friend.visibility = View.GONE
        else rl_add_friend.visibility = View.VISIBLE
    }
}