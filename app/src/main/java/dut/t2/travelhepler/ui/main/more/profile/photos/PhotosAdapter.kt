package dut.t2.travelhepler.ui.main.more.profile.photos

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import com.bumptech.glide.Glide
import dut.t2.travelhepler.R
import dut.t2.travelhepler.service.model.Photo
import kotlinx.android.synthetic.main.item_rcv_photos.view.*

class PhotosAdapter(val mContext: Context, val mPhotos: List<Photo>, var mCallback: ItemClickListener) :
    RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PhotoViewHolder {
        val view = LayoutInflater.from(this.mContext).inflate(R.layout.item_rcv_photos, p0, false)
        return PhotoViewHolder(view, view.img_photo, view.img_more_item_photos)
    }

    override fun getItemCount(): Int {
        return mPhotos.size
    }

    override fun onBindViewHolder(p0: PhotoViewHolder, p1: Int) {
        val item = mPhotos.get(p1)
        Glide.with(mContext).load(item.link)
            .placeholder(mContext.getDrawable(R.drawable.ic_user_circle))
            .override(100, 100)
            .centerCrop()
            .into(p0.photo)
    }

    inner class PhotoViewHolder(itemView: View, var photo: ImageView, var img_more: ImageView) :
        RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                mCallback.onClick(mPhotos[adapterPosition])
            }

            img_more.setOnClickListener {
                val popup = PopupMenu(mContext, img_more)
                popup.menuInflater.inflate(R.menu.menu_popup_delete, popup.menu)
                popup.setOnMenuItemClickListener { item ->
                    mCallback.onPopupItemClick(item.itemId, mPhotos[adapterPosition])
                    true
                }
                popup.show()
            }
        }

    }

    interface ItemClickListener {
        fun onClick(photo: Photo)

        fun onPopupItemClick(itemId: Int, photo: Photo)
    }
}