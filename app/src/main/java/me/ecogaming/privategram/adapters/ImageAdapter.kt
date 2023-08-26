package me.ecogaming.privategram.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.ecogaming.privategram.R
import me.ecogaming.privategram.entity.SideCard

class ImageAdapter(private val sideCards: List<SideCard>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val sideCard = sideCards[position]
        val imageUrl = sideCard.url

        // Use Glide to load and display the image
        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .into(holder.postImage) // imageView is the ImageView in your ViewHolder
    }


    override fun getItemCount(): Int {
        return sideCards.size
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val postImage: ImageView = itemView.findViewById(R.id.post_image)
    }
}
