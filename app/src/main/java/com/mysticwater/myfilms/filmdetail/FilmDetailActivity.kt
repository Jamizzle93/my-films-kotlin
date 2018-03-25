package com.mysticwater.myfilms.filmdetail

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.mysticwater.myfilms.R
import com.mysticwater.myfilms.data.source.FilmsRepository
import com.mysticwater.myfilms.data.source.remote.FilmsRemoteDataSource
import com.mysticwater.myfilms.util.replaceFragmentInActivity
import com.mysticwater.myfilms.util.setupActionBar
import com.squareup.picasso.Picasso

class FilmDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_film_detail)

        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        // Get the film ID
        val filmId = intent.getIntExtra(EXTRA_FILM_ID, 0)

        val filmDetailFragment = supportFragmentManager
                .findFragmentById(R.id.frame_content) as FilmDetailFragment?
                ?: FilmDetailFragment.newInstance(filmId).also {
            replaceFragmentInActivity(it, R.id.frame_content)
        }
        // Create the presenter
        val filmsRemoteDataSource = FilmsRemoteDataSource.getInstance()
        val filmsRepository: FilmsRepository = FilmsRepository.getInstance(filmsRemoteDataSource)
        val filmDetailPresenter: FilmDetailContract.Presenter = FilmDetailPresenter(filmId, filmsRepository, filmDetailFragment)

        val collapsingToolbar = findViewById<CollapsingToolbarLayout>(R.id.toolbar_collapsing)
        val appBarLayout = findViewById<AppBarLayout>(R.id.layout_app_bar)
        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {

            var isShow = true
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.title = ""
                    isShow = true
                } else if (isShow) {
                    collapsingToolbar.title = "TEST!"
                    isShow = false
                }
            }
        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    public fun setToolbarImage(backdropPath: String) {
        val toolbarImage = findViewById<ImageView>(R.id.film_backdrop)

        val imageUri = getString(R.string.tmdb_backdrop_w1280_url, backdropPath)
        val picassoBuilder = Picasso.Builder(this)
        picassoBuilder.build()
                .load(imageUri)
                .tag(this)
                .into(toolbarImage)
    }

    fun setTitle(title: String) {
        supportActionBar?.title = title
    }

    companion object {
        const val EXTRA_FILM_ID = "filmId"
    }

}
