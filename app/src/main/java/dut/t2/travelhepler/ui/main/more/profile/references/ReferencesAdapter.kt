package dut.t2.travelhepler.ui.main.more.profile.references

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.Reference
import kotlinx.android.synthetic.main.item_rcv_references.view.*

class ReferencesAdapter(
    val mContext: Context,
    val mReferences: List<Reference>,
    var mCallback: ReferencesClickListener
) : RecyclerView.Adapter<ReferencesAdapter.ReferencesViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ReferencesViewHolder {
        var view = LayoutInflater.from(this.mContext).inflate(R.layout.item_rcv_references, p0, false)
        return ReferencesViewHolder(
            view, view.cir_img_item_references, view.tv_receiver_name_item_my_request,
            view.tv_receiver_address_item_my_request, view.tv_positive_negative_item_references,
            view.tv_arr_dep_date_item_my_request
        )
    }

    override fun getItemCount(): Int {
        if (mReferences != null) return mReferences.size
        return 0
    }

    override fun onBindViewHolder(p0: ReferencesViewHolder, p1: Int) {
        val item = mReferences.get(p1)
        Glide.with(mContext).load(item.sender.avatar)
            .placeholder(mContext.getDrawable(R.drawable.ic_user_circle))
            .into(p0.cirImgSenderAvatar)
        p0.tvSenderName.text = item.sender.fullName
        p0.tvSenderAddress.text = item.sender.address
        if (item.status) {
            p0.tvPosNeg.setTextColor(ContextCompat.getColor(mContext, R.color.colorGreen))
            p0.tvPosNeg.text = mContext.getString(R.string.positive)
        } else p0.tvPosNeg.text = mContext.getString(R.string.negative)
        p0.tvPosNeg.setTextColor(ContextCompat.getColor(mContext, R.color.ios_red))
        p0.tvContent.text = item.content
    }

    inner class ReferencesViewHolder(
        itemView: View, var cirImgSenderAvatar: CircleImageView,
        var tvSenderName: TextView, var tvSenderAddress: TextView,
        var tvPosNeg: TextView, var tvContent: TextView
    ) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                mCallback.onClick(mReferences.get(adapterPosition))
            }
        }
    }

    interface ReferencesClickListener {
        fun onClick(reference: Reference)
    }
}