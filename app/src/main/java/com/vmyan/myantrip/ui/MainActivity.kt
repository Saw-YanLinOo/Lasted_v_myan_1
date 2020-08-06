package com.vmyan.myantrip.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.infideap.drawerbehavior.Advance3DDrawerLayout
import com.vmyan.myantrip.R
import com.vmyan.myantrip.ui.fragment.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_nav_layout.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawer: Advance3DDrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_nav_layout)

        drawer = findViewById<View>(R.id.drawer_layout) as Advance3DDrawerLayout

        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
        drawer_layout.setViewScale(GravityCompat.START, 0.96f)
        drawer.setRadius(GravityCompat.START, 30f)
        drawer.setViewElevation(GravityCompat.START, 8f)
        drawer.setViewRotation(GravityCompat.START, 15f)

        nav_button.setOnClickListener {
            if (!drawer.isDrawerOpen(GravityCompat.START)){
                drawer.openDrawer(GravityCompat.START)
            }else{
                drawer.closeDrawer(GravityCompat.END)
            }
        }


        bottom_nav_menu.setItemSelected(R.id.nav_home,true)
        openFragment(Home())
        bottom_nav_menu.setOnItemSelectedListener {
            when(it){
                R.id.nav_plan -> openFragment(Plan())
                R.id.nav_booking -> openFragment(Booking())
                R.id.nav_home -> openFragment(Home())
                R.id.nav_communication -> openFragment(Communication())
                R.id.nav_blog -> openFragment(Blog())
            }
        }
    }

    private fun openFragment(fragment: Fragment){

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Toast.makeText(applicationContext,"Menu Click",Toast.LENGTH_SHORT).show()
        return true
    }
}