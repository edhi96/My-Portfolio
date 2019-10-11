package com.sarwoedhi.moviecatalogue.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.sarwoedhi.moviecatalogue.R
import com.sarwoedhi.moviecatalogue.adapters.MovieAdapter
import com.sarwoedhi.moviecatalogue.models.Movie
import com.sarwoedhi.moviecatalogue.ui.SearchActivity.Companion.EXTRA_INFO_SEARCH
import com.sarwoedhi.moviecatalogue.ui.SearchActivity.Companion.EXTRA_QUERY
import com.sarwoedhi.moviecatalogue.viewmodels.MovieViewModel
import kotlinx.android.synthetic.main.fragment_movie.view.*

class MovieFragment : Fragment() {
    private var mAdapter: MovieAdapter? = null
    private var mProgressBar: ProgressBar? = null
    private var mMovieViewModel: MovieViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie, container, false)

        view.searchMovie.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val intent = Intent(activity, SearchActivity::class.java)
                intent.putExtra(EXTRA_QUERY, query)
                intent.putExtra(EXTRA_INFO_SEARCH, "SEARCH_MOVIE")
                startActivity(intent)
                view.searchMovie.clearFocus()
                view.searchMovie.setQuery("", true)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        mProgressBar = view.progressBar
        mAdapter = MovieAdapter(view.context)
        mAdapter?.notifyDataSetChanged()
        activity?.let {
            mMovieViewModel = ViewModelProviders.of(it).get(MovieViewModel::class.java)
            mMovieViewModel?.getMovies()?.observe(it, getMovies)

        }
        view.rvMovieList.layoutManager =
            GridLayoutManager(view.context, 2)
        view.rvMovieList.adapter = mAdapter
        return view
    }


    private val getMovies = Observer<ArrayList<Movie>> { movies ->
        if (!movies.isNullOrEmpty()) {
            mAdapter?.setData(movies)
            mProgressBar?.visibility = View.GONE
        } else {
            mProgressBar?.visibility = View.VISIBLE
        }
    }
}


