package dut.t2.travelhepler.ui.main.more.friends

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
import kotlinx.android.synthetic.main.item_rcv_friends.view.*

class FriendsAdapter(val mContext: Context, val mFriends: List<Profile>, var mCallback: FriendsClickListener) :
    RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FriendsViewHolder {
        var view = LayoutInflater.from(this.mContext).inflate(R.layout.item_rcv_friends, p0, false)
        return FriendsViewHolder(view, view.cir_img_avatar_friends, view.tv_user_name_friends, view.img_more_friend)
    }

    override fun getItemCount(): Int {
        return mFriends.size ?: 0
    }

    override fun onBindViewHolder(p0: FriendsViewHolder, p1: Int) {
        var friend = mFriends.get(p1);
        Glide.with(mContext).load(friend.avatar)
            .placeholder(mContext.getDrawable(R.drawable.ic_user_circle))
            .into(p0.cirImgFriendAvatar)
        p0.tvFriendName.text = friend.fullName
    }


    inner class FriendsViewHolder(
        itemView: View, var cirImgFriendAvatar: CircleImageView, var tvFriendName: TextView, var imgMore: ImageView
    ) : RecyclerView.ViewHolder(itemView) {
        init {

            itemView.setOnClickListener { mCallback.onClick(mFriends.get(adapterPosition)) }

            imgMore.setOnClickListener {
                var popup = PopupMenu(mContext, imgMore)
                popup.getMenuInflater().inflate(R.menu.menu_popup_friends, popup.getMenu())
                popup.setOnMenuItemClickListener { item ->
                    mCallback.onPopupItemClick(item.itemId, mFriends.get(adapterPosition))
                    true
                }
                popup.show()
            }
        }
    }

    interface FriendsClickListener {
        fun onClick(friend: Profile)

        fun onPopupItemClick(itemId: Int, friend: Profile)
    }

}