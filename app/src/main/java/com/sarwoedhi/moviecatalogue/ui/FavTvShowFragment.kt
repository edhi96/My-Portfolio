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
import com.sarwoedhi.moviecatalogue.adapters.FavTvShowAdapter
import com.sarwoedhi.moviecatalogue.models.TvShow
import com.sarwoedhi.moviecatalogue.viewmodels.FavTvShowViewModel
import kotlinx.android.synthetic.main.fragment_fav_tv_show.view.*

class FavTvShowFragment : Fragment() {
    private lateinit var mAdapter: FavTvShowAdapter
    private lateinit var mStatusInfo: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var mFavTvShowViewModel: FavTvShowViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav_tv_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.rvFavTvList.layoutManager = GridLayoutManager(view.context, 2)
        mStatusInfo = view.statusTvFav
        recyclerView = view.rvFavTvList
        mAdapter = FavTvShowAdapter(view.context)
        mFavTvShowViewModel = ViewModelProviders.of(this).get(FavTvShowViewModel::class.java)
        mFavTvShowViewModel.selectAllTvShow().observe(this@FavTvShowFragment, getTvShows)
        mAdapter.notifyDataSetChanged()
        recyclerView.adapter = mAdapter
    }

    override fun onResume() {
        super.onResume()
        mFavTvShowViewModel.selectAllTvShow().observe(this@FavTvShowFragment, getTvShows)
        recyclerView.adapter = mAdapter
    }

    private val getTvShows = Observer<List<TvShow>> { tvShow ->
        if (!tvShow.isNullOrEmpty()) {
            mAdapter.setData(tvShow)
            mAdapter.notifyDataSetChanged()
            mStatusInfo.visibility = View.GONE
        } else {
            mAdapter.notifyDataSetChanged()
            recyclerView.adapter = null
            mStatusInfo.text = activity?.getString(R.string.info_fav_movie)
            mStatusInfo.visibility = View.VISIBLE
        }
    }
}
