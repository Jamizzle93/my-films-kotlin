package com.mysticwater.myfilms.nowshowing

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.mysticwater.myfilms.R
import com.mysticwater.myfilms.adapter.FilmsAdapter
import com.mysticwater.myfilms.data.Film
import com.mysticwater.myfilms.filmdetail.FilmDetailActivity

class NowShowingFragment : Fragment(), NowShowingContract.View {

    override var presenter: NowShowingContract.Presenter? = null

    override var isActive: Boolean = false
        get() = isAdded


    override fun onResume() {
        super.onResume()
        presenter?.start()
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
            val filmsList = (findViewById<RecyclerView>(R.id.recycler_view_films)).apply {
                adapter = filmsAdapter
                layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
            }
            filmsList.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        }

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

    companion object {

        fun newInstance(): NowShowingFragment {
            return NowShowingFragment()
        }
    }
}
