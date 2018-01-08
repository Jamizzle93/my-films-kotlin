package com.mysticwater.myfilms.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mysticwater.myfilms.R
import com.mysticwater.myfilms.data.Film
import com.mysticwater.myfilms.util.loadUrl
import kotlinx.android.synthetic.main.view_film_row.view.*

class FilmsAdapter(val films: List<Film>) : RecyclerView.Adapter<FilmsAdapter.ViewHolder>() {

    private var mFilms: List<Film> = films

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.view_film_row, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val film = mFilms[position]
        
        holder.title.text = film.title

        val posterUrl = "https://image.tmdb.org/t/p/w500" + film.poster_path
        holder.poster.loadUrl(posterUrl)
    }

    override fun getItemCount(): Int {
        return mFilms.size
    }
    
    fun add(films: List<Film>) {
        mFilms = films
        notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val title = view.text_title
        val poster = view.image_poster
    }

}
