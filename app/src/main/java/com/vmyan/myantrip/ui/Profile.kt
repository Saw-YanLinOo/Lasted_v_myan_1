package com.vmyan.myantrip.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.vmyan.myantrip.R

class Profile : AppCompatActivity() {

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

    private fun setupObservers() {

    }

    private fun setupPostRecyclerView() {

    }

    private fun handleNetworkChanges() {

    }
}