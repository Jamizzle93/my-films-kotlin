package com.mysticwater.myfilms.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mysticwater.myfilms.R
import com.mysticwater.myfilms.data.Film
import com.mysticwater.myfilms.util.CalendarUtils
import com.mysticwater.myfilms.util.loadUrl
import kotlinx.android.synthetic.main.view_film_row.view.*
import java.math.RoundingMode
import java.text.DecimalFormat

class FilmsAdapter(val films: List<Film>, private val itemListener: FilmItemListener) : RecyclerView.Adapter<FilmsAdapter.ViewHolder>() {

    private var mFilms: List<Film> = films

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.view_film_row, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val film = mFilms[position]

        val posterUrl = "https://image.tmdb.org/t/p/w780" + film.backdrop_path
        holder.poster.loadUrl(posterUrl)

        holder.title.text = film.title

//        val decimalFormat = DecimalFormat("#.#")
//        decimalFormat.roundingMode = RoundingMode.CEILING
        holder.voteAverage.text = "%.1f".format(film.vote_average / 2)

        holder.voteRating.rating = film.vote_average / 2

        holder.voteCount.text = film.vote_count.toString()

        val releaseDate = CalendarUtils.calendarToString(film.release_date)
        holder.releaseDate.text = releaseDate

        holder.itemView.setOnClickListener { itemListener.onFilmClick(film) }
    }

    override fun getItemCount(): Int {
        return mFilms.size
    }

    fun add(films: List<Film>) {
        mFilms = films
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val poster = view.image_poster
        val title = view.text_title
        val voteAverage = view.text_vote_average
        val voteRating = view.rating_bar_vote
        val voteCount = view.text_vote_count
        val releaseDate = view.text_release_date
    }

    interface FilmItemListener {

        fun onFilmClick(clickedFilm: Film)

    }
}
