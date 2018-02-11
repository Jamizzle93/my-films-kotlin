package com.mysticwater.myfilms.filmdetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mysticwater.myfilms.R
import com.mysticwater.myfilms.data.source.FilmsRepository
import com.mysticwater.myfilms.data.source.remote.FilmsRemoteDataSource
import com.mysticwater.myfilms.util.replaceFragmentInActivity
import com.mysticwater.myfilms.util.setupActionBar

class FilmDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_film_detail)

//        setupActionBar(R.id.toolbar) {
//            setDisplayHomeAsUpEnabled(true)
//            setDisplayShowHomeEnabled(true)
//        }

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
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_FILM_ID = "filmId"
    }

}
