package com.vmyan.myantrip.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vmyan.myantrip.R

class PlaceItemDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_item_details)

        val place_id = intent.getStringExtra("place_id")

        println(place_id)
    }
}





