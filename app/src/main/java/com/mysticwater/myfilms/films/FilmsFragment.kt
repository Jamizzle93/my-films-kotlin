package com.mysticwater.myfilms.films

import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.mysticwater.myfilms.R
import com.mysticwater.myfilms.adapter.FilmsAdapter
import com.mysticwater.myfilms.data.Film
import com.mysticwater.myfilms.data.source.FilmType
import com.mysticwater.myfilms.filmdetail.FilmDetailActivity

class FilmsFragment : Fragment(), FilmsContract.View {

    override var presenter: FilmsContract.Presenter? = null

    override var isActive: Boolean = false
        get() = isAdded

    private lateinit var loadingProgressView: ProgressBar
    private lateinit var swipeContainer: SwipeRefreshLayout
    private lateinit var filmsList: RecyclerView
    private lateinit var noFilmsLayout: ConstraintLayout

    override fun onResume() {
        super.onResume()
        presenter?.start()

        if (arguments!!.containsKey(KEY_FILM_TYPE)) {
            val filmType = FilmType.valueOf(arguments!!.getString(KEY_FILM_TYPE))
            presenter?.loadFilms(filmType, true)
        }
    }

    internal var filmListener: FilmsAdapter.FilmItemListener = object : FilmsAdapter.FilmItemListener {
        override fun onFilmClick(clickedFilm: Film) {
            presenter?.openFilmDetail(clickedFilm)
        }
    }

    private val filmsAdapter = FilmsAdapter(ArrayList<Film>(0), filmListener)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_films, container, false)

        with(root) {
            loadingProgressView = findViewById(R.id.progress_loading)
            swipeContainer = findViewById(R.id.swipe_container)
            filmsList = (findViewById<RecyclerView>(R.id.recycler_view_films)).apply {
                adapter = filmsAdapter
                layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
            }
            noFilmsLayout = findViewById(R.id.layout_no_films)
        }

        swipeContainer.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorPrimary))

        return root
    }

    override fun showFilms(films: List<Film>) {
        filmsAdapter.add(films)
    }

    override fun showFilmDetailUi(filmId: Int) {
        val intent = Intent(context, FilmDetailActivity::class.java).apply {
            putExtra(FilmDetailActivity.EXTRA_FILM_ID, filmId)
        }
        startActivity(intent)
    }

    override fun showLoadingUi(active: Boolean) {
        if (active) {
            loadingProgressView.visibility = View.VISIBLE
            filmsList.visibility = View.INVISIBLE
        } else {
            loadingProgressView.visibility = View.INVISIBLE
            filmsList.visibility = View.VISIBLE
        }
    }

    override fun showNoFilms() {
        filmsList.visibility = View.INVISIBLE
        noFilmsLayout.visibility = View.VISIBLE
    }

    companion object {

        const val KEY_FILM_TYPE = "filmType"

        fun newInstance(): FilmsFragment {
            return FilmsFragment()
        }
    }
}
