package com.sarwoedhi.moviecatalogue.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sarwoedhi.moviecatalogue.R
import com.sarwoedhi.moviecatalogue.adapters.FavMovieAdapter
import com.sarwoedhi.moviecatalogue.models.Movie
import com.sarwoedhi.moviecatalogue.viewmodels.FavMovieViewModel
import kotlinx.android.synthetic.main.fragment_fav_movie.view.*


class FavMovieFragment : Fragment() {
    private lateinit var mAdapter: FavMovieAdapter
    private var mStatusInfo: TextView? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var mFavMovieViewModel: FavMovieViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fav_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.rvFavMovieList.layoutManager = GridLayoutManager(view.context, 2)
        mStatusInfo = view.statusMovieFav
        recyclerView = view.rvFavMovieList
        mAdapter = FavMovieAdapter(view.context)
        mFavMovieViewModel = ViewModelProviders.of(this).get(FavMovieViewModel::class.java)
        mFavMovieViewModel.selectAllMovies().observe(this, getMovies)
        mAdapter.notifyDataSetChanged()
        recyclerView.adapter = mAdapter
    }


    override fun onResume() {
        super.onResume()
        mFavMovieViewModel.selectAllMovies().observe(this, getMovies)
    }

    private val getMovies = Observer<List<Movie>> { movies ->
        if (!movies.isNullOrEmpty()) {
            mAdapter.setData(movies)
            mAdapter.notifyDataSetChanged()
            recyclerView.adapter = mAdapter
            mStatusInfo?.visibility = View.GONE
        } else {
            mAdapter.notifyDataSetChanged()
            recyclerView.adapter = null
            mStatusInfo?.text = activity?.getString(R.string.info_fav_movie)
            mStatusInfo?.visibility = View.VISIBLE
        }
    }
}
