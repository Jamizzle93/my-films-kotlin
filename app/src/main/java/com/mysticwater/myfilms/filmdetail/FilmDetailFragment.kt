package com.mysticwater.myfilms.filmdetail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mysticwater.myfilms.R
import com.mysticwater.myfilms.data.Film

class FilmDetailFragment : Fragment(), FilmDetailContract.View {

    private lateinit var filmTitle: TextView

    override var presenter: FilmDetailContract.Presenter? = null

    override var isActive: Boolean = false
        get() = isAdded

    override fun onResume() {
        super.onResume()
        presenter?.start()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_film_detail, container, false)
        setHasOptionsMenu(true)
        with(root) {
            filmTitle = findViewById(R.id.text_title)
        }

        return root
    }

    override fun setLoadingIndicator(active: Boolean) {
        if (active) {
            filmTitle.text = ""
        }
    }

    override fun showFilm(film: Film) {
        with(filmTitle) {
            text = film.title
        }
    }

    override fun showNoFilm() {
        // TODO
    }

    companion object {

        private val ARGUMENT_FILM_ID = "filmId"

        fun newInstance(filmId: Int) =
                FilmDetailFragment().apply {
                    arguments = Bundle().apply { putInt(ARGUMENT_FILM_ID, filmId) }
                }
    }

}
