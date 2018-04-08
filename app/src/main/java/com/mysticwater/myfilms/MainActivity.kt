package com.mysticwater.myfilms

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.mysticwater.myfilms.data.source.FilmType
import com.mysticwater.myfilms.data.source.FilmsRepository
import com.mysticwater.myfilms.data.source.remote.FilmsRemoteDataSource
import com.mysticwater.myfilms.films.FilmsFragment
import com.mysticwater.myfilms.films.FilmsPresenter
import com.mysticwater.myfilms.util.ActivityUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = getString(R.string.app_name)

        val navbar = findViewById<BottomNavigationView>(R.id.nav_bottom)
        navbar.selectedItemId = R.id.menu_now_showing
        navbar.setOnNavigationItemSelectedListener { item ->
            selectFragment(item)
            true
        }
        navbar.setOnNavigationItemReselectedListener({ item -> selectFragment(item) })

        showFragment(FilmType.NOW_SHOWING)
    }

    fun selectFragment(item: MenuItem) {

        item.isChecked = true

        when (item.itemId) {
            R.id.menu_now_showing -> {
                showFragment(FilmType.NOW_SHOWING)
            }
            R.id.menu_upcoming -> {
                showFragment(FilmType.UPCOMING)
            }
            R.id.menu_favourite -> {
                showFragment(FilmType.FAVOURITES)
            }
        }
    }

    fun showFragment(filmType: FilmType) {
        val filmsFragment = FilmsFragment.newInstance()

        val args = Bundle()
        args.putString(FilmsFragment.KEY_FILM_TYPE, filmType.name)

        filmsFragment.arguments = args

        ActivityUtils.replaceFragmentInActivity(supportFragmentManager, filmsFragment, R.id.layout_content)

        val filmsRemoteDataSource = FilmsRemoteDataSource.getInstance()
        val filmsRepository = FilmsRepository.getInstance(filmsRemoteDataSource)
        val filmsPresenter: FilmsPresenter = FilmsPresenter(filmsRepository, filmsFragment)
    }

}
