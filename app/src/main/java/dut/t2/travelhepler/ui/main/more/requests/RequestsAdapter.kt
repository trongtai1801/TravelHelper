package dut.t2.travelhepler.ui.main.more.requests

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import dut.t2.travelhelper.service.model.Profile
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.Request
import dut.t2.travelhepler.utils.CalendarUtils
import kotlinx.android.synthetic.main.item_rcv_references.view.tv_arr_dep_date_item_my_request
import kotlinx.android.synthetic.main.item_rcv_references.view.tv_receiver_address_item_my_request
import kotlinx.android.synthetic.main.item_rcv_references.view.tv_receiver_name_item_my_request
import kotlinx.android.synthetic.main.item_rcv_requests.view.*

class RequestsAdapter(
    val mContext: Context,
    val mRequests: List<Request>,
    var mCallback: RequestClickListener
) : RecyclerView.Adapter<RequestsAdapter.RequestsViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RequestsViewHolder {
        var view = LayoutInflater.from(this.mContext).inflate(R.layout.item_rcv_requests, p0, false)
        return RequestsViewHolder(
            view, view.cir_img_receiver_avatar_my_requests, view.tv_receiver_name_item_my_request,
            view.tv_receiver_address_item_my_request, view.cir_img_delete_my_requests,
            view.tv_arr_dep_date_item_my_request
        )
    }

    override fun getItemCount(): Int {
        if (mRequests != null) return mRequests.size
        return 0
    }

    override fun onBindViewHolder(p0: RequestsViewHolder, p1: Int) {
        val item = mRequests.get(p1)
        Glide.with(mContext).load(item.receiver.avatar)
            .placeholder(mContext.getDrawable(R.drawable.ic_user_circle))
            .into(p0.cirImgReceiverAvatar)
        p0.tvReceiverName.text = item.receiver.fullName
        p0.tvReceiverAddress.text = item.receiver.address
        p0.tvArrDepDate.text = CalendarUtils.convertStringFormat(item.arrivalDate.split("T")[0]) +
                "-" + CalendarUtils.convertStringFormat(item.departureDate.split("T")[0])
    }

    inner class RequestsViewHolder(
        itemView: View, var cirImgReceiverAvatar: CircleImageView,
        var tvReceiverName: TextView, var tvReceiverAddress: TextView,
        var cirImgDeleteMyRequest: CircleImageView, var tvArrDepDate: TextView
    ) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                mCallback.onClick(mRequests.get(adapterPosition))
            }
            cirImgDeleteMyRequest.setOnClickListener {
                mCallback.onDelete(mRequests.get(adapterPosition))
            }
            tvReceiverName.setOnClickListener {
                mCallback.onReceiverClick(mRequests.get(adapterPosition).receiver)
            }
        }
    }

    interface RequestClickListener {
        fun onClick(request: Request)

        fun onDelete(request: Request)

        fun onReceiverClick(receiver: Profile)
    }
}