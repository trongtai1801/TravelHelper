package dut.t2.travelhepler.ui.travelers

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.PublicTrip
import dut.t2.travelhepler.utils.CalendarUtils
import kotlinx.android.synthetic.main.item_rcv_hosts.view.*

class TravlersAdapter(
    val mContext: Context,
    val mTravelers: List<PublicTrip>,
    var mCallback: HostClickListener
) : RecyclerView.Adapter<TravlersAdapter.HostViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): HostViewHolder {
        var view = LayoutInflater.from(this.mContext).inflate(R.layout.item_rcv_travelers, p0, false)
        return HostViewHolder(
            view, view.img_avatar_item_host, view.tv_user_name_host,
            view.tv_arr_dep, view.tv_fluent_language_host
        )
    }

    override fun getItemCount(): Int {
        if (mTravelers != null) return mTravelers.size
        return 0
    }

    override fun onBindViewHolder(p0: HostViewHolder, p1: Int) {
        val item = mTravelers.get(p1).user
        Glide.with(mContext).load(item!!.avatar)
            .placeholder(mContext.getDrawable(R.drawable.ic_user_circle))
            .into(p0.imgAvatar)
        p0.tvUserName.text = item!!.fullName
        p0.tvAddress.text = mContext.getString(R.string.visiting) + " " + mTravelers.get(p1).destination
        p0.tvFluentLanguage.text = CalendarUtils.convertStringFormat(mTravelers.get(p1).splitArrivalDate()) + "-" +
                CalendarUtils.convertStringFormat(mTravelers.get(p1).splitDepartureDate()) +
                "(" + mTravelers.get(p1).travelerNumber + mContext.getString(R.string.travelers) + ")"
    }

    inner class HostViewHolder(
        itemView: View, var imgAvatar: ImageView,
        var tvUserName: TextView, var tvAddress: TextView, var tvFluentLanguage: TextView
    ) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                mCallback.onClick(mTravelers.get(adapterPosition))
            }
        }
    }

    interface HostClickListener {
        fun onClick(traveler: PublicTrip)
    }
}