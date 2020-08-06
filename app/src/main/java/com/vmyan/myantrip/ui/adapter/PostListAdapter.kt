package com.vmyan.myantrip.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.smarteist.autoimageslider.SliderAnimations
import com.vmyan.myantrip.data.entities.Post
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.Profile
import kotlinx.android.synthetic.main.post_recyclerviews.view.*

class PostListAdapter(private val listener: PostListAdapter.ItemClickListener,private val postList:MutableList<Post>) : RecyclerView.Adapter<PostListAdapter.BlogViewHolder>(){



    interface ItemClickListener {
        fun onPostClick(position : Int)
    }
    fun setItems(postList: List<Post>){
        this.postList.clear()
        this.postList.addAll(postList)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_recyclerviews,parent,false)
        return BlogViewHolder(view,listener)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        holder.bind(postList[position])
    }
    class BlogViewHolder(private val view: View, private val listener: ItemClickListener): RecyclerView.ViewHolder(view),View.OnClickListener{

        private  val imageSliderAdapter = PostImageSliderAdapter()
        private var  imageList = ArrayList<String>()
        private lateinit var post : Post

        init {
            view.setOnClickListener(this)
            view.btn_comment.setOnClickListener(this)
        }
        fun bind( post: Post){
            this.post = post
            Glide.with(itemView) //1
                .load(post.profile)
                .placeholder(R.drawable.profile)
                .skipMemoryCache(true) //2
                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                .transform(CircleCrop()) //4
                .into(itemView.img_profile)

            itemView.tv_username.text = post.username
            itemView.tv_descripton.text = post.description
            itemView.tv_Like.text = post.post_like
//            if (post.post_view != "")itemView.tv_viwer.text = post.post_view

            if (imageList.size!=0){
                imageList.clear()
            }else{
                if ( post.post_image_1 != "") imageList.add(post.post_image_1)
                if (post.post_image_2 != "") imageList.add(post.post_image_2)
                if ( post.post_image_3 != "") imageList.add(post.post_image_3)
                if ( post.post_image_4 != "") imageList.add(post.post_image_4)
                if ( post.post_image_5 != "") imageList.add(post.post_image_5)
            }
            imageSliderAdapter.setItems(imageList!!)
            itemView.imageSlider.setSliderAdapter(imageSliderAdapter)
            itemView.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            itemView.imageSlider.setScrollTimeInSec(1000);

            itemView.img_profile.setOnClickListener{
                val intent = Intent(view.context, Profile::class.java)
                intent.putExtra("user_id",post.user_id)
                view.context.startActivity(intent)

            }
            itemView.btn_like.setOnClickListener{
                println("You Like ${post.username}")
            }
            itemView.btn_follow.setOnClickListener{
                println("You Follow ${post.username}")
            }
        }

        override fun onClick(p0: View?) {
            listener.onPostClick(adapterPosition)
        }
    }
}