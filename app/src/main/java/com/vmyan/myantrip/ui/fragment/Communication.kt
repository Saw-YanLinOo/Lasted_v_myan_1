package com.vmyan.myantrip.ui.fragment

import android.animation.*
import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.widget.AppCompatSpinner
import androidx.fragment.app.*
import androidx.lifecycle.*
import androidx.recyclerview.widget.*
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.adapter.WordAdapter
import com.vmyan.myantrip.ui.viewmodel.CommuViewModel
import com.vmyan.myantrip.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_blog.view.*
import kotlinx.android.synthetic.main.fragment_communication.*
import kotlinx.android.synthetic.main.fragment_communication.view.*
import www.sanju.zoomrecyclerlayout.ZoomRecyclerLayout

@AndroidEntryPoint
class Communication : Fragment() , WordAdapter.ItemClickListener{

    private val viewModel : CommuViewModel by viewModels()
    val languageList = listOf("myanmar","english")
    val language = MutableLiveData<String>()
    private lateinit var imageAnimation: AnimationDrawable
    private lateinit var wordAdapter: WordAdapter

    init {
//        language.value = Hawk.get("language","english")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_communication, container, false)
        setupAnimation(view)
        setupSpinner(view)
        setupObservers(view)
        setupWordRecyclerView(view)
        handleNetworkChanges(view)
        return view;

    }

    private fun setupAnimation(view: View) {
        val imageView = view.findViewById<ImageView>(R.id.img_view_animation)
        imageView.apply {
            setBackgroundResource(R.drawable.speak_animation_list)
            imageAnimation = background as AnimationDrawable

        }
        imageAnimation.start()
    }

    private fun setupSpinner(view: View) {
        val spinner: AppCompatSpinner = view.findViewById(R.id.spinner_language)

        val adapter = context?.let { ArrayAdapter(it,android.R.layout.simple_dropdown_item_1line,languageList) }
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
//                language.value = Hawk.get("language","english")
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                language.value = languageList[p2]
            }
        }
    }

    private fun getAllData(){
        viewModel.getWord()
    }

    private fun setupWordRecyclerView(view: View) {
        wordAdapter = WordAdapter(this, mutableListOf())

        view.wordRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        view.wordRecyclerView.addItemDecoration (DividerItemDecoration (context, (view.wordRecyclerView.layoutManager as LinearLayoutManager) .orientation))
        view.wordRecyclerView.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
        }
        view.wordRecyclerView.adapter = wordAdapter
    }

    private fun setupObservers(view: View) {
        viewModel.wordLiveData.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Resource.Status.SUCCESS->{
                    it.data?.let { it1-> wordAdapter.setItems(it1) }
                    println("${it.message}")
                }
                Resource.Status.ERROR-> println(it.message)
                Resource.Status.LOADING-> println("Loading")
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun handleNetworkChanges(view: View) {
        activity?.let {
            NetworkUtils.getNetworkLiveData(it).observe(viewLifecycleOwner, Observer { isConnected ->
                println("STATE CHANGED = $isConnected")
                if (!isConnected) {
                    getAllData()
                    view.connection_status_c.text = "No Connection"
                    view.connection_status_layout_c.apply {
                        show()
                        getColorRes(R.color.colorStatusNotConnected)?.let { it1 ->
                            setBackgroundColor(
                                it1
                            )
                        }
                    }
                } else {
                    getAllData()
                    view.connection_status_c.text = "Back Online"
                    view.connection_status_layout_c.apply {
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

    override fun onWordClick(position: Int) {
    println("Click Item")
    }

}