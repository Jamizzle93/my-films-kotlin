package com.mysticwater.myfilms

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = getString(R.string.app_name)

        val navbar = findViewById<BottomNavigationView>(R.id.nav_bottom)
        navbar.selectedItemId = R.id.menu_now_showing
    }

}
