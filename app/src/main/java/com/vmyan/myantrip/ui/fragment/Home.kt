package com.vmyan.myantrip.ui.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.PlaceItemDetails
import com.vmyan.myantrip.ui.adapter.HomePlaceListAdapter
import com.vmyan.myantrip.ui.adapter.PlaceCategoryAdapter
import com.vmyan.myantrip.ui.viewmodel.HomeViewModel
import com.vmyan.myantrip.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.view.*
import www.sanju.zoomrecyclerlayout.ZoomRecyclerLayout


@AndroidEntryPoint
class Home : Fragment(), PlaceCategoryAdapter.ItemClickListener, HomePlaceListAdapter.ItemClickListener {

    private val viewModel: HomeViewModel by viewModels()
    private val subPlaceCategoryList = ArrayList<String>()
    private lateinit var catAdapter: PlaceCategoryAdapter
    private lateinit var homePlaceListAdapter: HomePlaceListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        setupObservers(view)
        setUpPlaceCategoryRecycler(view)
        setUpHomePlaceRecycler(view)
        handleNetworkChanges(view)
        onSubCatTabClick(view)

        return view
    }

    private fun setUpPlaceCategoryRecycler(view: View){
        catAdapter = PlaceCategoryAdapter(this, mutableListOf())
        view.home_cat_recycler.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        view.home_cat_recycler.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
        }

        val snapHelperStart: SnapHelper = GravitySnapHelper(Gravity.START)
        snapHelperStart.attachToRecyclerView(view.home_cat_recycler)
        view.home_place_list_recycler.isNestedScrollingEnabled = false
        view.home_cat_recycler.adapter = catAdapter
    }

    private fun setUpHomePlaceRecycler(view: View){
        homePlaceListAdapter = HomePlaceListAdapter(this, mutableListOf())
        val layoutManager = activity?.let { ZoomRecyclerLayout(it) }
        layoutManager!!.orientation = LinearLayoutManager.HORIZONTAL
        layoutManager.reverseLayout = false
        layoutManager.stackFromEnd = false
        view.home_place_list_recycler.layoutManager = layoutManager
        view.home_place_list_recycler.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
        }

        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(view.home_place_list_recycler)
        view.home_place_list_recycler.isNestedScrollingEnabled = false

        view.home_place_list_recycler.adapter = homePlaceListAdapter

    }


    private fun setupObservers(view: View) {
        viewModel.subPlaceCategoryLiveData.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Resource.Status.SUCCESS -> {
                    subPlaceCategoryList.clear()
                    for (data in it.data!!){
                        if (subPlaceCategoryList.size < it.data.size ){
                            subPlaceCategoryList.add(data.sub_cat_name)
                        }
                    }
                    view.sub_cat_tab_layout.setTabData(subPlaceCategoryList)

            }
                Resource.Status.ERROR ->
                    println(it.message)
                Resource.Status.LOADING ->
                    println("Loading")
            }
        })

        viewModel.placeCategoryLiveData.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Resource.Status.SUCCESS -> {
                    it.data?.let { it1 -> catAdapter.setItems(it1) }
                }
                Resource.Status.ERROR ->
                    println(it.message)
                Resource.Status.LOADING ->
                    println("Loading")
            }
        })

        viewModel.rplaceLiveData.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Resource.Status.SUCCESS -> {
                    it.data?.let { it1 -> homePlaceListAdapter.setItems(it1) }
                }
                Resource.Status.ERROR ->
                    println(it.message)
                Resource.Status.LOADING ->
                    println("Loading")
            }
        })


    }

    private fun getAllData() {
        viewModel.getSubPlaceCategory()
        viewModel.getPlaceCategory()
        viewModel.getPlace()
        viewModel.getRecommendedPlace()
    }

    @SuppressLint("SetTextI18n")
    private fun handleNetworkChanges(view: View) {
        activity?.let {
            NetworkUtils.getNetworkLiveData(it).observe(viewLifecycleOwner, Observer { isConnected ->
                println("STATE CHANGED = $isConnected")
                if (!isConnected) {
                    getAllData()
                    view.connection_status.text = "No Connection"
                    view.connection_status_layout.apply {
                        show()
                        getColorRes(R.color.colorStatusNotConnected)?.let { it1 ->
                            setBackgroundColor(
                                it1
                            )
                        }
                    }
                } else {
                    getAllData()
                    view.connection_status.text = "Back Online"
                    view.connection_status_layout.apply {
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

    override fun onItemClick(cat_id: Int) {
        Toast.makeText(activity,cat_id.toString(),Toast.LENGTH_SHORT).show()
    }

    private fun onSubCatTabClick(view: View){
        view.sub_cat_tab_layout.setOnTabSelectListener { it ->
            if (it == 0) {
                view.home_place_list_recycler.smoothScrollToPosition(0)
                viewModel.getRecommendedPlace()
                viewModel.rplaceLiveData.observe(viewLifecycleOwner, Observer {
                    when(it.status){
                        Resource.Status.SUCCESS -> {
                            it.data?.let { it1 -> homePlaceListAdapter.setItems(it1) }
                        }
                        Resource.Status.ERROR ->
                            println(it.message)
                        Resource.Status.LOADING ->
                            println("Loading")
                    }
                })
            }else if (it == 1){
                view.home_place_list_recycler.smoothScrollToPosition(0)
                viewModel.getTopRatingPlace()
                viewModel.tPplaceLiveData.observe(viewLifecycleOwner, Observer {
                    when(it.status){
                        Resource.Status.SUCCESS -> {
                            it.data?.let { it1 -> homePlaceListAdapter.setItems(it1) }
                        }
                        Resource.Status.ERROR ->
                            println(it.message)
                        Resource.Status.LOADING ->
                            println("Loading")
                    }
                })

            }

            if (it >= 2){
                view.home_place_list_recycler.smoothScrollToPosition(0)
                viewModel.getSubCategoryPlaces(subPlaceCategoryList[it])
                viewModel.subplaceLiveData.observe(viewLifecycleOwner, Observer {
                    when(it.status){
                        Resource.Status.SUCCESS -> {
                            it.data?.let { it1 -> homePlaceListAdapter.setItems(it1) }
                        }
                        Resource.Status.ERROR ->
                            println(it.message)
                        Resource.Status.LOADING ->
                            println("Loading")
                    }
                })
            }
        }
    }



    override fun onPlaceClick(place_id: Int) {
        val intent = Intent(activity,PlaceItemDetails::class.java)
        intent.putExtra("place_id",place_id.toString())
        startActivity(intent)
    }

}