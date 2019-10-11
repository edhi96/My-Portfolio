package com.sarwoedhi.moviecatalogue.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.sarwoedhi.moviecatalogue.R
import com.sarwoedhi.moviecatalogue.adapters.MovieAdapter
import com.sarwoedhi.moviecatalogue.adapters.TvShowAdapter
import com.sarwoedhi.moviecatalogue.models.Movie
import com.sarwoedhi.moviecatalogue.models.TvShow
import com.sarwoedhi.moviecatalogue.viewmodels.SearchViewModel
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast

class SearchActivity : AppCompatActivity() {

    private lateinit var infoSearch: String
    private lateinit var querySearch: String
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var tvShowAdapter: TvShowAdapter
    private lateinit var searchViewModel: SearchViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setActionBarTitle(getString(R.string.search_result))
        movieAdapter = MovieAdapter(this)
        tvShowAdapter = TvShowAdapter(this)
        val query: String?
        val info: String?
        if (intent != null) {
            query = intent.getStringExtra(EXTRA_QUERY)
            querySearch = query
            info = intent.getStringExtra(EXTRA_INFO_SEARCH)
            infoSearch = info
        }
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        resultSearch.setQuery(querySearch, false)
        resultSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    progressBarSearch?.visibility = View.VISIBLE
                    loadResults(query)
                    resultSearch.clearFocus()
                    progressBarSearch?.visibility = View.GONE
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        when (infoSearch) {
            "SEARCH_MOVIE" -> {
                searchViewModel.getResultSearchMovies(querySearch)
                    .observe(this@SearchActivity, getResultMovies)
                rvResultSearchList.adapter = movieAdapter
            }
            "SEARCH_TV" -> {
                searchViewModel.getResultSearchTvShows(querySearch)
                    .observe(this@SearchActivity, getResultTv)
                rvResultSearchList.adapter = tvShowAdapter
            }
        }
        rvResultSearchList.layoutManager = GridLayoutManager(this, 2)
    }

    private fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    private fun loadResults(query: String) {
        when (infoSearch) {
            "SEARCH_MOVIE" -> {
                searchViewModel.doAsync {
                    searchViewModel.getResultSearchMovies(query)
                }
                if (searchViewModel.getResultSearchMovies(query).value != null) {
                    movieAdapter = MovieAdapter(this)
                    movieAdapter.notifyDataSetChanged()
                    rvResultSearchList.adapter = movieAdapter
                } else {
                    progressBarSearch?.visibility = View.GONE
                }
            }
            "SEARCH_TV" -> {
                searchViewModel.doAsync {
                    searchViewModel.getResultSearchTvShows(query)
                }
                if (searchViewModel.getResultSearchTvShows(query).value != null) {
                    tvShowAdapter = TvShowAdapter(this)
                    tvShowAdapter.notifyDataSetChanged()
                    rvResultSearchList.adapter = tvShowAdapter
                } else {
                    progressBarSearch?.visibility = View.GONE
                }
            }
        }
    }

    private val getResultMovies = Observer<ArrayList<Movie>> { movies ->
        if (movies != null) {
            movieAdapter.setData(movies)
            progressBarSearch?.visibility = View.GONE
        } else {
            toast("Not found!")
            progressBarSearch?.visibility = View.VISIBLE
        }
    }
    private val getResultTv = Observer<ArrayList<TvShow>> { tvShow ->
        if (tvShow != null) {
            tvShowAdapter.setData(tvShow)
            progressBarSearch?.visibility = View.GONE
        } else {
            toast("Not found!")
            progressBarSearch?.visibility = View.VISIBLE
        }
    }

    companion object {
        const val EXTRA_QUERY = "EXTRA_QUERY"
        const val EXTRA_INFO_SEARCH = "EXTRA_INFO_SEARCH"
    }
}
