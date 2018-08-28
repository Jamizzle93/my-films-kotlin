package com.mysticwater.myfilms.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mysticwater.myfilms.R
import com.mysticwater.myfilms.data.Film
import kotlinx.android.synthetic.main.view_film_row.view.*

class FilmsAdapter(val films: List<Film>, private val itemListener: FilmItemListener) : RecyclerView.Adapter<FilmsAdapter.ViewHolder>() {

    private var mFilms: List<Film> = films

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.view_film_row, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val film = mFilms[position]

        // TODO - Setup the film
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
