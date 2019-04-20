package dut.t2.travelhepler.ui.main.dashboard

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.PublicTrip
import kotlinx.android.synthetic.main.item_rcv_upcoming_travel_dashboard.view.*

class PublicTripAdapter(val mContext: Context, val mPublicTrips: List<PublicTrip>, var mCallback: ItemClickListener) :
    RecyclerView.Adapter<PublicTripAdapter.PublicTripViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PublicTripViewHolder {
        var view = LayoutInflater.from(this.mContext).inflate(R.layout.item_rcv_upcoming_travel_dashboard, p0, false);
        return PublicTripViewHolder(
            view,
            view.tv_item_upcoming_trip_location,
            view.tv_item_upcoming_trip_previous,
            view.img_more_item_upcoming_trip_location
        )
    }

    override fun getItemCount(): Int {
        return mPublicTrips.size
    }

    override fun onBindViewHolder(p0: PublicTripViewHolder, p1: Int) {
        val item = mPublicTrips.get(p1)
        p0.tv_location.text = item.destination
        p0.tv_previous.text = item.arrivalDate.split("T")[0] + " - " + item.departureDate.split("T")[0]
    }

    inner class PublicTripViewHolder(
        itemView: View, var tv_location: TextView,
        var tv_previous: TextView, var img_more: ImageView
    ) :
        RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener(View.OnClickListener {
                mCallback.onClick(mPublicTrips.get(adapterPosition))
            })
            itemView.setOnLongClickListener {
                var popup = PopupMenu(mContext, img_more)
                popup.getMenuInflater().inflate(R.menu.menu_popup_edit_delete, popup.getMenu())
                popup.setOnMenuItemClickListener { item ->
                    mCallback.onPopupItemClick(item.itemId, mPublicTrips.get(adapterPosition))
                    true
                }
                popup.show()
                true
            }
            img_more.setOnClickListener {
                var popup = PopupMenu(mContext, img_more)
                popup.getMenuInflater().inflate(R.menu.menu_popup_edit_delete, popup.getMenu())
                popup.setOnMenuItemClickListener { item ->
                    mCallback.onPopupItemClick(item.itemId, mPublicTrips.get(adapterPosition))
                    true
                }
                popup.show()
            }
        }
    }

    interface ItemClickListener {
        fun onClick(publicTrip: PublicTrip)

        fun onPopupItemClick(itemId: Int, trip: PublicTrip)
    }
}