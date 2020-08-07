package com.vmyan.myantrip.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.adapter.ProfilePostAdapter
import com.vmyan.myantrip.ui.viewmodel.BlogViewModel
import com.vmyan.myantrip.utils.NetworkUtils
import com.vmyan.myantrip.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.context_profile.*

@AndroidEntryPoint
class Profile : AppCompatActivity(),ProfilePostAdapter.ItemClickListener {
    private val viewModel : BlogViewModel by viewModels()
    private lateinit var profilePostAdapter: ProfilePostAdapter
    private var id : Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        id = intent.getIntExtra("user_id",1)
        Toast.makeText(applicationContext,"user_id -> ${id}", Toast.LENGTH_SHORT).show()

        setSupportActionBar(findViewById(R.id.toolbar_p))
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout_p).title = "Saw Yan Lin Oo"
//        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }

        setupObservers()
        setupPostRecyclerView()
        handleNetworkChanges()
    }

    private fun getAllData() {
        viewModel.getPostByUserId(id)
    }

    private fun setupObservers() {
        viewModel.profilePostLiveData.observe(this, Observer {
            when(it.status){
                Resource.Status.SUCCESS->{
                    it.data?.let { it1-> profilePostAdapter.setItems(it1) }
                }
                Resource.Status.ERROR-> println(it.message)
                Resource.Status.LOADING-> println("Loading")
            }
        })
    }

    private fun setupPostRecyclerView() {
        profilePostAdapter = ProfilePostAdapter( this,mutableListOf())
        profile_recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        profile_recyclerView.adapter = profilePostAdapter
    }

    @SuppressLint("SetTextI18n")
    private fun handleNetworkChanges() {
        NetworkUtils.getNetworkLiveData(applicationContext).observe(this, Observer { isConnected ->
            println("STATE CHANGED = $isConnected")
            if (!isConnected) {
                getAllData()
            } else {
                getAllData()
            }
        })

    }

    override fun onPostClick(position: Int) {
        println("Item Click")
    }
}