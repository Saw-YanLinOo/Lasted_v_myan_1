package com.vmyan.myantrip.ui.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.adapter.PostListAdapter
import com.vmyan.myantrip.ui.viewmodel.BlogViewModel
import com.vmyan.myantrip.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_blog.view.*
import www.sanju.zoomrecyclerlayout.ZoomRecyclerLayout

@AndroidEntryPoint
class Blog : Fragment(),PostListAdapter.ItemClickListener {

    private val viewModel : BlogViewModel by viewModels()
    private lateinit var postListAdapter: PostListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_blog, container, false)

        setupObservers(view)
        setupPostRecyclerView(view)
        handleNetworkChanges(view)

        return view
    }
    private fun getAllData() {
        viewModel.getPost()
    }

    private fun setupPostRecyclerView(view: View) {
        postListAdapter = PostListAdapter(this, mutableListOf())
        val layoutManager = activity?.let { ZoomRecyclerLayout(it) }
        layoutManager!!.orientation = LinearLayoutManager.VERTICAL
        view.post_recyclerView.layoutManager = layoutManager
        view.post_recyclerView.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
        }

        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(view.post_recyclerView)
        view.post_recyclerView.adapter = postListAdapter
    }

    private fun setupObservers(view: View) {
        viewModel.postLiveData.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Resource.Status.SUCCESS->{
                    it.data?.let { it1-> postListAdapter.setItems(it1) }
                }
                Resource.Status.ERROR-> println(it.message)
                Resource.Status.LOADING-> println("Loading")
            }
        })
    }

    override fun onPostClick(position: Int) {
        println("Item Click")
    }

    @SuppressLint("SetTextI18n")
    private fun handleNetworkChanges(view: View) {
        activity?.let {
            NetworkUtils.getNetworkLiveData(it).observe(viewLifecycleOwner, Observer { isConnected ->
                println("STATE CHANGED = $isConnected")
                if (!isConnected) {
                    getAllData()
                    view.connection_statuss.text = "No Connection"
                    view.connection_status_layouts.apply {
                        show()
                        getColorRes(R.color.colorStatusNotConnected)?.let { it1 ->
                            setBackgroundColor(
                                it1
                            )
                        }
                    }
                } else {
                    getAllData()
                    view.connection_statuss.text = "Back Online"
                    view.connection_status_layouts.apply {
                        getColorRes(R.color.colorStatusConnected)?.let { it1 ->
                            setBackgroundColor(
                                it1
                            )
                        }
                        animate()
                            .alpha(1f)
                            .setStartDelay(Constants.ANIMATION_DURATION)
                            .setDuration(Constants.ANIMATION_DURATION)
                            .setListener(object : AnimatorListenerAdapter(){
                                override fun onAnimationEnd(animation: Animator?) {
                                    hide()
                                }
                            })
                    }
                }
            })
        }
    }

}