package dut.t2.travelhepler.ui.main.dashboard

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.PublicTrip
import kotlinx.android.synthetic.main.item_rcv_upcoming_travel_dashboard.view.*

class PublicTripAdapter(val context: Context, val publicTrips: List<PublicTrip>, var callback: ItemClickListener) :
    RecyclerView.Adapter<PublicTripAdapter.PublicTripViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PublicTripViewHolder {
        var view = LayoutInflater.from(this.context).inflate(R.layout.item_rcv_upcoming_travel_dashboard, p0, false);
        return PublicTripViewHolder(view, view.tv_item_upcoming_trip_location, view.tv_item_upcoming_trip_previous)
    }

    override fun getItemCount(): Int {
        return publicTrips.size
    }

    override fun onBindViewHolder(p0: PublicTripViewHolder, p1: Int) {
        val item = publicTrips.get(p1)
        p0.tv_location.text = item.destination
        p0.tv_previous.text = item.arrivalDate.split("T")[0] + " - " + item.departureDate.split("T")[0]
    }

    inner class PublicTripViewHolder(itemView: View, var tv_location: TextView, var tv_previous: TextView) :
        RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener(View.OnClickListener {
                callback.onClick(publicTrips.get(adapterPosition))
            })
        }
    }

    interface ItemClickListener {
        fun onClick(publicTrip: PublicTrip)
    }
}