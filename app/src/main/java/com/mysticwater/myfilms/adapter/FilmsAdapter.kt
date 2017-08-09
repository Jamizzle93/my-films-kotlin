package com.mysticwater.myfilms.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mysticwater.myfilms.R
import com.mysticwater.myfilms.data.Film
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
    }

}
