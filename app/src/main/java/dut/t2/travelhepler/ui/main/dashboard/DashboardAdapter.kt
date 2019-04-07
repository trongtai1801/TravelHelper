package dut.t2.travelhepler.ui.main.dashboard

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.DashboarItem
import kotlinx.android.synthetic.main.item_rcv_dashboard.view.*

class DashboardAdapter(val context: Context, val dashboarItems: List<DashboarItem>, var callback: ItemClickListener) :
    RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DashboardViewHolder {
        var view = LayoutInflater.from(this.context).inflate(R.layout.item_rcv_dashboard, p0, false);
        return DashboardViewHolder(view, view.tv_item_rcv_dashboard, view.img_item_rcv_dashboard)
    }

    override fun getItemCount(): Int {
        return dashboarItems.size
    }

    override fun onBindViewHolder(p0: DashboardViewHolder, p1: Int) {
        val item = dashboarItems.get(p1)
        p0.tv.text = item.name
        Glide.with(context).load(item.image)
            .placeholder(context.getDrawable(R.drawable.host))
            .into(p0.img)
    }

    inner class DashboardViewHolder(itemView: View, var tv: TextView, var img: ImageView) :
        RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener(View.OnClickListener {
                callback.onClick(dashboarItems.get(adapterPosition))
            })
        }
    }

    interface ItemClickListener {
        fun onClick(dashboarItem: DashboarItem)
    }
}