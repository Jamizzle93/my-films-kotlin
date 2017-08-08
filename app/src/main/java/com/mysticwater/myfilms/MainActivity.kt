package com.mysticwater.myfilms

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navbar = findViewById<BottomNavigationView>(R.id.nav_bottom)
        navbar.setOnNavigationItemReselectedListener({ item -> selectFragment(item) })
    }

    fun selectFragment(item: MenuItem) {

        item.isChecked = true

        when (item.itemId) {
            R.id.menu_now_showing -> {
                // TODO - Show fragment
            }
            R.id.menu_upcoming -> {
                // TODO - Show fragment
            }
            R.id.menu_favourite -> {
                // TODO - Show fragment
            }
        }
    }
}
