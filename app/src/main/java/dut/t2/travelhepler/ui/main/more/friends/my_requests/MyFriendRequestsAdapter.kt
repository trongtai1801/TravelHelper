package dut.t2.travelhepler.ui.main.more.friends.my_requests

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import dut.t2.travelhelper.service.model.Profile
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.FriendRequest
import kotlinx.android.synthetic.main.item_rcv_friends.view.cir_img_avatar_friends
import kotlinx.android.synthetic.main.item_rcv_friends.view.img_more_friend
import kotlinx.android.synthetic.main.item_rcv_friends.view.tv_user_name_friends
import kotlinx.android.synthetic.main.item_rcv_requests_friends.view.*

class MyFriendRequestsAdapter(
    val mContext: Context,
    val mRequests: List<FriendRequest>,
    var mCallback: RequestClickListener
) : RecyclerView.Adapter<MyFriendRequestsAdapter.FriendRequestViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FriendRequestViewHolder {
        var view = LayoutInflater.from(this.mContext).inflate(R.layout.item_rcv_requests_friends, p0, false)
        return FriendRequestViewHolder(
            view, view.cir_img_avatar_friends, view.tv_user_name_friends, view.tv_status_friend, view.img_more_friend
        )
    }

    override fun getItemCount(): Int {
        if (mRequests != null) return mRequests.size
        return 0
    }

    override fun onBindViewHolder(p0: FriendRequestViewHolder, p1: Int) {
        val item = mRequests.get(p1)
        Glide.with(mContext).load(item.receiver.avatar)
            .placeholder(mContext.getDrawable(R.drawable.ic_user_circle))
            .into(p0.cirImgSenderAvatar)
        p0.tvSenderName.text = item.receiver.fullName

        if (item.isDeleted != null && item.isDeleted) {
            p0.tvStatus.text = mContext.getString(R.string.ignored)
            p0.tvStatus.setTextColor(mContext.resources.getColor(R.color.ios_red))
        } else {
            if (item.isAccepted != null && item.isAccepted) {
                p0.tvStatus.text = mContext.getString(R.string.accepted)
                p0.tvStatus.setTextColor(mContext.resources.getColor(R.color.colorGreen))
            } else {
                p0.cirImgDeleteMyRequest.visibility = View.VISIBLE
                p0.tvStatus.text = mContext.getString(R.string.waiting)
                p0.tvStatus.setTextColor(mContext.resources.getColor(R.color.ios_red))
            }
        }
    }

    inner class FriendRequestViewHolder(
        itemView: View, var cirImgSenderAvatar: CircleImageView,
        var tvSenderName: TextView, var tvStatus: TextView,
        var cirImgDeleteMyRequest: ImageView
    ) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                mCallback.onClick(mRequests.get(adapterPosition).receiver)
            }
            cirImgDeleteMyRequest.setOnClickListener {
                var popup = PopupMenu(mContext, cirImgDeleteMyRequest)
                popup.menuInflater.inflate(R.menu.menu_popp_up_cancel, popup.getMenu())
                popup.setOnMenuItemClickListener { item ->
                    mCallback.onPopupItemClick(item.itemId, mRequests.get(adapterPosition))
                    true
                }
                popup.show()
            }
        }
    }

    interface RequestClickListener {

        fun onClick(sender: Profile)

        fun onPopupItemClick(itemId: Int, request: FriendRequest)
    }
}