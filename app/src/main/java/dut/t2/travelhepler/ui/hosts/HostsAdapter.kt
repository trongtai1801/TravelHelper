package dut.t2.travelhepler.ui.hosts

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import dut.t2.travelhelper.service.model.Profile
import dut.t2.travelhepler.R
import kotlinx.android.synthetic.main.item_rcv_hosts.view.*

class HostsAdapter(
    val mContext: Context,
    val mHosts: List<Profile>,
    var mCallback: HostClickListener
) : RecyclerView.Adapter<HostsAdapter.HostViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): HostViewHolder {
        var view = LayoutInflater.from(this.mContext).inflate(R.layout.item_rcv_hosts, p0, false)
        return HostViewHolder(
            view, view.img_avatar_item_host, view.tv_user_name_host,
            view.tv_address_host, view.tv_fluent_language_host
        )
    }

    override fun getItemCount(): Int {
        if (mHosts != null) return mHosts.size
        return 0
    }

    override fun onBindViewHolder(p0: HostViewHolder, p1: Int) {
        val item = mHosts.get(p1)
        Glide.with(mContext).load(item.avatar)
            .placeholder(mContext.getDrawable(R.drawable.ic_user_circle))
            .into(p0.imgAvatar)
        p0.tvUserName.text = item.fullName
        p0.tvAddress.text = item.address
        p0.tvFluentLanguage.text = item.fluentLanguage
    }

    inner class HostViewHolder(
        itemView: View, var imgAvatar: ImageView,
        var tvUserName: TextView, var tvAddress: TextView, var tvFluentLanguage: TextView
    ) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                mCallback.onClick(mHosts.get(adapterPosition))
            }
        }
    }

    interface HostClickListener {
        fun onClick(host: Profile)
    }
}