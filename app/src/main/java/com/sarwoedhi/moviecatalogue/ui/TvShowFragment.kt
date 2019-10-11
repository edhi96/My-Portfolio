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
import com.sarwoedhi.moviecatalogue.adapters.TvShowAdapter
import com.sarwoedhi.moviecatalogue.models.TvShow
import com.sarwoedhi.moviecatalogue.ui.SearchActivity.Companion.EXTRA_INFO_SEARCH
import com.sarwoedhi.moviecatalogue.ui.SearchActivity.Companion.EXTRA_QUERY
import com.sarwoedhi.moviecatalogue.viewmodels.TvShowViewModel
import kotlinx.android.synthetic.main.fragment_tv_show.view.*

class TvShowFragment : Fragment() {

    private var mAdapter: TvShowAdapter? = null
    private var mProgressBarTv: ProgressBar? = null
    private var mTvShowViewModel: TvShowViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tv_show, container, false)

        view.searchTv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val intent = Intent(activity, SearchActivity::class.java)
                intent.putExtra(EXTRA_QUERY, query)
                intent.putExtra(EXTRA_INFO_SEARCH, "SEARCH_TV")
                startActivity(intent)
                view.searchTv.clearFocus()
                view.searchTv.setQuery("", true)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        mAdapter = TvShowAdapter(view.context)
        mProgressBarTv = view.progress_TvShow
        mAdapter?.notifyDataSetChanged()
        activity?.let {
            mTvShowViewModel = ViewModelProviders.of(it).get(TvShowViewModel::class.java)
            mTvShowViewModel?.getTvShows()?.observe(it, getTvShows)
            view.rvTvShowList.adapter = mAdapter
        }
        view.rvTvShowList.layoutManager =
            GridLayoutManager(view.context, 2)
        view.rvTvShowList.adapter = mAdapter
        return view
    }

    private val getTvShows = Observer<ArrayList<TvShow>> { tvShows ->
        if (!tvShows.isNullOrEmpty()) {
            mAdapter?.setData(tvShows)
            mProgressBarTv?.visibility = View.GONE
        } else {
            mProgressBarTv?.visibility = View.VISIBLE
        }
    }
}
