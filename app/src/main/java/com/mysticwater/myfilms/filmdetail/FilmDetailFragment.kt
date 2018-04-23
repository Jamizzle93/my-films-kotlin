package com.mysticwater.myfilms.filmdetail

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.TextView
import com.mysticwater.myfilms.R
import com.mysticwater.myfilms.data.Film
import com.mysticwater.myfilms.util.CalendarUtils
import com.squareup.picasso.Callback

class FilmDetailFragment : Fragment(), FilmDetailContract.View {

    private lateinit var loadingProgress: ProgressBar
    private lateinit var filmLayout: ConstraintLayout
    private lateinit var title: TextView
    private lateinit var voteAverage: TextView
    private lateinit var voteAverageRatingBar: RatingBar
    private lateinit var voteCount: TextView
    private lateinit var overviewLayout: ConstraintLayout
    private lateinit var overview: TextView
    private lateinit var releaseDate: TextView
    private lateinit var runtime: TextView
    private lateinit var favourite: TextView
    private lateinit var imdb: TextView

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
            loadingProgress = findViewById(R.id.progress_loading)
            filmLayout = findViewById(R.id.layout_film_detail)

            title = findViewById(R.id.text_title)
            voteAverage = findViewById(R.id.text_vote_average)
            voteAverageRatingBar = findViewById(R.id.rating_bar_vote)
            voteCount = findViewById(R.id.text_vote_count)
            overviewLayout = findViewById(R.id.layout_overview)
            overviewLayout.setOnClickListener {
                if (overview.maxLines == 3)
                    overview.maxLines = Integer.MAX_VALUE
                else
                    overview.maxLines = 3
            }
            overview = findViewById(R.id.text_overview)
            releaseDate = findViewById(R.id.text_release_date)
            runtime = findViewById(R.id.text_runtime)
            favourite = findViewById(R.id.text_favourite)
            imdb = findViewById(R.id.text_imdb)
            imdb.setOnClickListener {

            }
        }

        return root
    }

    override fun setLoadingIndicator(active: Boolean) {
        if (active) {
            loadingProgress.visibility = View.VISIBLE
            filmLayout.visibility = View.INVISIBLE
        } else {
            loadingProgress.visibility = View.GONE
            filmLayout.visibility = View.VISIBLE
        }
    }

    override fun showFilm(film: Film) {
        with(title) {
            text = film.title
        }
        with(voteAverage)
        {
            text = (film.vote_average / 2).toString()
        }
        with(voteAverageRatingBar)
        {
            rating = film.vote_average / 2
        }
        with(voteCount)
        {
            text = film.vote_count.toString()
        }
        with(overview) {
            text = film.overview
        }
        with(releaseDate) {
            val releaseDate = CalendarUtils.calendarToString(film.release_date)
            text = releaseDate
        }
        with(runtime) {
            text = runtimeToHoursMinutes(film.runtime)
        }

        if (activity is FilmDetailActivity) {

            val parentActivity: FilmDetailActivity = activity as FilmDetailActivity

            val backdropPath: String? = film.backdrop_path
            if (backdropPath != null) {
                parentActivity.setToolbarImage(backdropPath, object : Callback {
                    override fun onSuccess() {
                        setLoadingIndicator(false)
                    }

                    override fun onError() {
                        setLoadingIndicator(false)
                    }

                })
            }

            parentActivity.setTitle(film.title)
        }

        var favouriteDrawable = R.drawable.ic_favorite
        if (film.favourite) {
            favouriteDrawable = R.drawable.ic_favorite_fill
        }

        favourite.setCompoundDrawablesWithIntrinsicBounds(0, favouriteDrawable, 0, 0)


        favourite.setOnClickListener {
            if (film.favourite) {
                favourite.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_favorite, 0, 0)
            } else {
                favourite.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_favorite_fill, 0, 0)
            }
            presenter?.favouriteFilm(film)
        }
    }

    private fun runtimeToHoursMinutes(runtime: Int): String {
        val hours = runtime / 60
        val minutes = runtime % 60

        val hoursStr = resources.getQuantityString(R.plurals.hours, hours, hours)
        val minutesStr = resources.getQuantityString(R.plurals.minutes, minutes, minutes)

        return hoursStr + " " + minutesStr
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
