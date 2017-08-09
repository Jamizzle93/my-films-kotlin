package com.mysticwater.myfilms.nowshowing

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.mysticwater.myfilms.R
import com.mysticwater.myfilms.adapter.FilmsAdapter
import com.mysticwater.myfilms.data.Film

class NowShowingFragment : Fragment(), NowShowingContract.View {

    override var presenter: NowShowingContract.Presenter? = null

    override var isActive: Boolean = false
        get() = isAdded

    private val filmsAdapter = FilmsAdapter(ArrayList<Film>(0))

    override fun onResume() {
        super.onResume()
        presenter?.start()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_films, container, false)

        with(root) {
            val filmsList = (findViewById<RecyclerView>(R.id.recycler_view_films)).apply {
                adapter = filmsAdapter
                layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
            }
        }

        return root
    }

    override fun showFilms(films: List<Film>) {
        filmsAdapter.add(films)
    }

    companion object {

        fun newInstance(): NowShowingFragment {
            return NowShowingFragment()
        }
    }
}
