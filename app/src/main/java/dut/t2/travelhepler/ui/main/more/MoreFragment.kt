package dut.t2.travelhepler.ui.main.more

import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.View
import com.bumptech.glide.Glide
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.FriendRequest
import dut.t2.travelhepler.ui.main.MainActivity
import dut.t2.travelhepler.ui.main.more.friends.FriendsActivity_
import dut.t2.travelhepler.ui.main.more.friends.waiting_requests.WaitingFriendRequestsActivity_
import dut.t2.travelhepler.ui.main.more.offer.my_offer.MyOffersActivity_
import dut.t2.travelhepler.ui.main.more.offer.wating_offer.WaitingOffersActivity_
import dut.t2.travelhepler.ui.main.more.profile.ProfileActivity_
import dut.t2.travelhepler.ui.main.more.requests.my_requests.MyRequestsActivity_
import dut.t2.travelhepler.ui.main.more.requests.wating_requests.WaitingTravelRequestsActivity_
import dut.t2.travelhepler.ui.splash.SplashActivity_
import dut.t2.travelhepler.utils.Constant
import dut.t2.travelhepler.utils.RealmDAO
import dut.t2.travelhepler.utils.SharedPrefs
import kotlinx.android.synthetic.main.fragment_more.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EFragment

@EFragment(R.layout.fragment_more)
class MoreFragment : Fragment() {

    @AfterViews
    fun afterViews() {
        setupViews()
    }

    @Click(
        R.id.btn_logout, R.id.csl_profile_more, R.id.ln_my_friend_request_more,
        R.id.ln_fiend_more, R.id.ln_my_requests_more, R.id.ln_waiting_requests_more, R.id.ln_waiting_friend_more,
        R.id.ln_my_offer_more, R.id.ln_waiting_offer_more
    )
    fun onClick(v: View) {
        when (v.id) {
            R.id.csl_profile_more -> {
                ProfileActivity_.intent(context).startForResult(Constant.REQUEST_CODE_UPDATE_USER_AVATAR)
            }
            R.id.ln_my_friend_request_more -> {
                (activity as MainActivity).showToast("trips")
            }
            R.id.ln_fiend_more -> {
                FriendsActivity_.intent(context).start()
            }
            R.id.ln_my_requests_more -> {
                MyRequestsActivity_.intent(context).start()
            }
            R.id.ln_waiting_requests_more -> {
                WaitingTravelRequestsActivity_.intent(context).start()
            }
            R.id.ln_my_offer_more -> {
                MyOffersActivity_.intent(context).start()
            }
            R.id.ln_waiting_offer_more -> {
                WaitingOffersActivity_.intent(context).start()
            }
            R.id.ln_waiting_requests_more -> {
                WaitingTravelRequestsActivity_.intent(context).start()
            }
            R.id.ln_waiting_friend_more -> {
                WaitingFriendRequestsActivity_.intent(context).start()
            }
            R.id.btn_logout -> {
                showConfirmLogoutDialog()
            }
        }
    }

    fun showConfirmLogoutDialog() {
        val alertDialog = AlertDialog.Builder(context!!)
            .setTitle(R.string.confirm_logout)
            .setPositiveButton(R.string.yes, { dialogInterface, i -> logout() })
            .setNegativeButton(R.string.no, null)
            .create()
        alertDialog.getWindow()!!.setBackgroundDrawableResource(R.drawable.background_dialog)
        alertDialog.show()
    }

    fun logout() {
        SharedPrefs.getInstance().put(Constant.ACCESS_TOKEN, "")
        SplashActivity_.intent(activity).start()
        activity!!.finish()
    }

    fun setupViews() {
        tv_user_name_more.text = RealmDAO.getProfileLogin()!!.fullName
        Glide.with(context!!).load(RealmDAO.getProfileLogin()!!.avatar)
            .placeholder(context!!.getDrawable(R.drawable.ic_user_circle))
            .into(cir_img_avatar_more)
    }
}