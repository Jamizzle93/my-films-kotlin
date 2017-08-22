package com.mysticwater.myfilms

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.mysticwater.myfilms.data.source.FilmsRepository
import com.mysticwater.myfilms.data.source.remote.FilmsRemoteDataSource
import com.mysticwater.myfilms.nowshowing.NowShowingFragment
import com.mysticwater.myfilms.nowshowing.NowShowingPresenter
import com.mysticwater.myfilms.util.ActivityUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navbar = findViewById<BottomNavigationView>(R.id.nav_bottom)
        navbar.selectedItemId = R.id.menu_upcoming
        showNowShowingFragment()
        navbar.setOnNavigationItemReselectedListener({ item -> selectFragment(item) })

    }

    fun selectFragment(item: MenuItem) {

        item.isChecked = true

        when (item.itemId) {
            R.id.menu_now_showing -> {
                // TODO - Show fragment
                showNowShowingFragment()
            }
            R.id.menu_upcoming -> {
                // TODO - Show fragment
            }
            R.id.menu_favourite -> {
                // TODO - Show fragment
            }
        }
    }

    fun showNowShowingFragment() {
        var nowShowingFragment = NowShowingFragment.newInstance()
        ActivityUtils.addFragmentToActivity(supportFragmentManager, nowShowingFragment, R.id.layout_content)

        val filmsRemoteDataSource = FilmsRemoteDataSource.getInstance()
        val filmsRepository = FilmsRepository.getInstance(filmsRemoteDataSource)

        var nowShowingPresenter: NowShowingPresenter = NowShowingPresenter(filmsRepository, nowShowingFragment)
    }

}
