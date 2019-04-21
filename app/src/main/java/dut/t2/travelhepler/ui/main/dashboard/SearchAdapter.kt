package dut.t2.travelhepler.ui.main.dashboard

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.SearchItem
import dut.t2.travelhepler.utils.Constant
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
        var imageDefault: Drawable? = null
        when (item.id) {
            Constant.ID_SEARCH_ITEM_HOST -> {
                imageDefault = context.getDrawable(R.drawable.host)
            }
            Constant.ID_SEARCH_ITEM_TRAVELERS -> {
                imageDefault = context.getDrawable(R.drawable.tralver)
            }
            Constant.ID_SEARCH_ITEM_EVENT -> {
                imageDefault = context.getDrawable(R.drawable.event)
            }
        }
        Glide.with(context).load(item.image)
            .placeholder(imageDefault)
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