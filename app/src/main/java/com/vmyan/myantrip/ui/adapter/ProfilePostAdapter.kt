package com.vmyan.myantrip.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.vmyan.myantrip.R
import com.vmyan.myantrip.data.entities.ProfilePost
import kotlinx.android.synthetic.main.profile_item_recyclerview.view.*

class ProfilePostAdapter(private val listener: ProfilePostAdapter.ItemClickListener,private val postList:MutableList<ProfilePost>) : RecyclerView.Adapter<ProfilePostAdapter.BlogViewHolder>() {

    interface ItemClickListener {
        fun onPostClick(position : Int)
    }
    fun setItems(postList:List<ProfilePost>){
        this.postList.clear()
        this.postList.addAll(postList)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.profile_item_recyclerview,parent,false)
        return BlogViewHolder(view,listener)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        holder.bind(postList[position])
    }

    class BlogViewHolder(private val view: View, private val listener: ProfilePostAdapter.ItemClickListener):RecyclerView.ViewHolder(view),View.OnClickListener{
        init {
            view.setOnClickListener(this)
        }
        fun bind(postItem: ProfilePost){
            Glide.with(itemView)
                .load(postItem.post_image_1)
                .placeholder(R.drawable.profile)
                .into(itemView.img_post_image)

            Glide.with(itemView) //1
                .load(postItem.profile)
                .placeholder(R.drawable.profile)
                .skipMemoryCache(true) //2
                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                .transform(CircleCrop()) //4
                .into(itemView.img_profiles)
            itemView.tv_username.text = postItem.username
        }

        override fun onClick(p0: View?) {
            listener.onPostClick(adapterPosition)
        }
    }
}