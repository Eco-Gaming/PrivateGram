package me.ecogaming.privategram.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import me.ecogaming.privategram.R
import me.ecogaming.privategram.entity.Post

class ProfileFeedAdapter(private val onClick: (Post) -> Unit, private val posts: List<Post>) : RecyclerView.Adapter<ProfileFeedAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_profile_feed_post, parent, false)
        return PostViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    inner class PostViewHolder(itemView: View, val onClick: (Post) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val profileFeedImage: ImageView = itemView.findViewById(R.id.profile_feed_image)
        private var currentPost: Post? = null

        init {
            profileFeedImage.setOnClickListener {
                currentPost?.let {
                    onClick(it)
                }
            }
        }

        fun bind(post: Post) {
            currentPost = post

            val imageUrl = post.thumb
            Picasso.get().load(imageUrl).into(profileFeedImage)
        }
    }
}