package dut.t2.travelhepler.ui.travelers.info

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import dut.t2.travelhelper.base.BaseActivity
import dut.t2.travelhelper.service.model.Profile
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.Home
import dut.t2.travelhepler.service.model.PublicTrip
import dut.t2.travelhepler.ui.main.more.profile.home.HomeActivity_
import dut.t2.travelhepler.ui.main.more.profile.photos.PhotosActivity_
import dut.t2.travelhepler.ui.main.more.profile.references.ReferencesActivity_
import dut.t2.travelhepler.ui.travelers.offer.OfferActivity_
import dut.t2.travelhepler.utils.CalendarUtils
import dut.t2.travelhepler.utils.Constant
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
import kotlinx.android.synthetic.main.activity_traveler_info.*
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_traveler_info)
class TravelerInfoActivity : BaseActivity<TravelerInfoContract.TravelerInfoView, TravelerInfoPresenterImpl>(),
    TravelerInfoContract.TravelerInfoView {

    private var mTrip: PublicTrip? = null
    private var mTraveler: Profile? = null

    companion object {
        private var sIsFriend = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mTrip = intent.getParcelableExtra(Constant.TRIP)
        mTraveler = mTrip!!.user
    }

    override fun initPresenter() {
        mPresenter = TravelerInfoPresenterImpl(this)
    }

    override fun afterViews() {
        initToolbar()
        checkFriend()
        setupViews()
    }

    @Click(
        R.id.tv_lb_photos_profile, R.id.tv_lb_reference_profile,
        R.id.tv_lb_home_profile, R.id.btn_offer_host, R.id.btn_add_friend
    )
    fun onClick(v: View) {
        when (v.id) {
            R.id.tv_lb_photos_profile -> {
                PhotosActivity_.intent(this).extra(Constant.USER_ID, mTraveler!!.id).start()
            }
            R.id.tv_lb_reference_profile -> {
                ReferencesActivity_.intent(this).extra(Constant.USER_ID, mTraveler!!.id).start()
            }
            R.id.tv_lb_home_profile -> {
                showLoading()
                mPresenter!!.getHomeInfo(mTraveler!!.id)
            }
            R.id.btn_offer_host -> {
                OfferActivity_.intent(this).extra(Constant.TRAVELER, mTrip).start()
            }
            R.id.btn_add_friend -> {
                showToast("add friend")
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

    fun initToolbar() {
        setSupportActionBar(toolbar_show_profile)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        title = mTraveler!!.fullName
        Glide.with(this).load(mTraveler!!.avatar)
            .placeholder(this.getDrawable(R.drawable.ic_user_circle))
            .into(img_avatar_toolbar)
        toolbar_show_profile.setNavigationOnClickListener { view -> onBackPressed() }
    }

    fun setupViews() {
        btn_offer_host.visibility = View.VISIBLE
        tv_destination.text = mTrip!!.destination
        tv_arr_dep.text = CalendarUtils.convertStringFormat(mTrip!!.splitArrivalDate()) + "-" +
                CalendarUtils.convertStringFormat(mTrip!!.splitDepartureDate())
        tv_trip_description_tra_info.text = mTrip!!.description
        tv_content_address_profile.text = mTraveler!!.address
        if (!mTraveler!!.splitBirthday()!!.equals(""))
            tv_content_birthday_profile.text =
                CalendarUtils.convertStringFormat(mTraveler!!.splitBirthday()!!)
        else tv_content_birthday_profile.text = ""
        if (mTraveler!!.gender) {
            tv_content_gender_profile.text = getString(R.string.male)
        } else
            tv_content_gender_profile.text = getString(R.string.female)
        tv_content_occupation_profile.text = mTraveler!!.address
        tv_content_fluence_profile.text = mTraveler!!.fluentLanguage
        tv_content_learning_profile.text = mTraveler!!.learningLanguage
        tv_content_about_me_profile.text = mTraveler!!.about
        tv_content_interest_profile.text = mTraveler!!.interest
        showHideAddFriendButton()
    }

    fun checkFriend() {
        showLoading()
        mPresenter!!.checkFriend(mTraveler!!.id)
    }

    fun showHideAddFriendButton() {
        if (sIsFriend) rl_add_friend.visibility = View.GONE
        else rl_add_friend.visibility = View.VISIBLE
    }
}