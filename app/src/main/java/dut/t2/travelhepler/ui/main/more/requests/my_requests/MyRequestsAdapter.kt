package dut.t2.travelhepler.ui.main.more.requests.my_requests

import android.annotation.SuppressLint
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
import dut.t2.travelhepler.service.model.Request
import dut.t2.travelhepler.utils.CalendarUtils
import kotlinx.android.synthetic.main.item_rcv_my_requests.view.*
import kotlinx.android.synthetic.main.item_rcv_references.view.tv_arr_dep_date_item_my_request
import kotlinx.android.synthetic.main.item_rcv_references.view.tv_receiver_address_item_my_request
import kotlinx.android.synthetic.main.item_rcv_references.view.tv_receiver_name_item_my_request
import kotlinx.android.synthetic.main.item_rcv_requests.view.*
import kotlinx.android.synthetic.main.item_rcv_requests.view.cir_img_delete_my_requests
import kotlinx.android.synthetic.main.item_rcv_requests.view.cir_img_receiver_avatar_my_requests
import kotlinx.android.synthetic.main.item_rcv_requests.view.tv_status_my_request

class MyRequestsAdapter(
    val mContext: Context,
    val mRequests: List<Request>,
    var mCallback: RequestClickListener
) : RecyclerView.Adapter<MyRequestsAdapter.RequestsViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RequestsViewHolder {
        var view = LayoutInflater.from(this.mContext).inflate(R.layout.item_rcv_requests, p0, false)
        return RequestsViewHolder(
            view, view.cir_img_receiver_avatar_my_requests, view.tv_receiver_name_item_my_request,
            view.tv_receiver_address_item_my_request, view.cir_img_delete_my_requests,
            view.tv_arr_dep_date_item_my_request, view.tv_status_my_request, view.img_more_request
        )
    }

    override fun getItemCount(): Int {
        return mRequests.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(p0: RequestsViewHolder, p1: Int) {
        val item = mRequests.get(p1)
        Glide.with(mContext).load(item.receiver.avatar)
            .placeholder(mContext.getDrawable(R.drawable.ic_user_circle))
            .into(p0.cirImgReceiverAvatar)
        p0.tvReceiverName.text = item.receiver.fullName
        p0.tvReceiverAddress.text = mContext.getString(R.string.travel_to) + " " + item.receiver.address
        p0.tvArrDepDate.text = CalendarUtils.convertStringFormat(item.arrivalDate.split("T")[0]) +
                "-" + CalendarUtils.convertStringFormat(item.departureDate.split("T")[0])

        if (item.isDeleted) {
            p0.tvStatus.text = mContext.getString(R.string.ignored)
            p0.tvStatus.setTextColor(mContext.resources.getColor(R.color.ios_red))
        } else {
            if (item.isAccepted) {
                p0.tvStatus.text = mContext.getString(R.string.accepted)
                p0.tvStatus.setTextColor(mContext.resources.getColor(R.color.colorGreen))
            } else {
                p0.tvStatus.text = mContext.getString(R.string.waiting)
                p0.tvStatus.setTextColor(mContext.resources.getColor(R.color.ios_red))
            }
        }
    }

    inner class RequestsViewHolder(
        itemView: View, var cirImgReceiverAvatar: CircleImageView,
        var tvReceiverName: TextView, var tvReceiverAddress: TextView,
        var cirImgDeleteMyRequest: CircleImageView, var tvArrDepDate: TextView,
        var tvStatus: TextView, var mImgMore: ImageView
    ) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                mCallback.onClick(mRequests[adapterPosition])
            }
            cirImgDeleteMyRequest.setOnClickListener {
                mCallback.onDelete(mRequests[adapterPosition])
            }
            tvReceiverName.setOnClickListener {
                mCallback.onReceiverClick(mRequests[adapterPosition].receiver)
            }
            mImgMore.setOnClickListener {
                val popup = PopupMenu(mContext, cirImgDeleteMyRequest)
                popup.menuInflater.inflate(R.menu.menu_popp_up_cancel, popup.menu)
                popup.setOnMenuItemClickListener { item ->
                    mCallback.onPopupItemClick(item.itemId, mRequests[adapterPosition])
                    true
                }
                popup.show()
            }
        }
    }

    interface RequestClickListener {
        fun onClick(request: Request)

        fun onDelete(request: Request)

        fun onReceiverClick(receiver: Profile)

        fun onPopupItemClick(itemId: Int, request: Request)
    }
}