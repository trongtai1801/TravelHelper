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
import dut.t2.travelhepler.service.model.SearchItem
import kotlinx.android.synthetic.main.item_rcv_search_dashboard.view.*

class SearchAdapter(val context: Context, val searchItems: List<SearchItem>, var callback: ItemClickListener) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SearchViewHolder {
        var view = LayoutInflater.from(this.context).inflate(R.layout.item_rcv_search_dashboard, p0, false);
        return SearchViewHolder(view, view.tv_item_rcv_dashboard, view.img_item_rcv_dashboard)
    }

    override fun getItemCount(): Int {
        return searchItems.size
    }

    override fun onBindViewHolder(p0: SearchViewHolder, p1: Int) {
        val item = searchItems.get(p1)
        p0.tv.text = item.name
        Glide.with(context).load(item.image)
            .placeholder(context.getDrawable(R.drawable.host))
            .into(p0.img)
    }

    inner class SearchViewHolder(itemView: View, var tv: TextView, var img: ImageView) :
        RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener(View.OnClickListener {
                callback.onClick(searchItems.get(adapterPosition))
            })
        }
    }

    interface ItemClickListener {
        fun onClick(searchItem: SearchItem)
    }
}