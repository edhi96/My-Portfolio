package com.sarwoedhi.moviecatalogue.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sarwoedhi.moviecatalogue.R
import com.sarwoedhi.moviecatalogue.adapters.FavPagerAdapter
import kotlinx.android.synthetic.main.fragment_fav.view.*

class FavFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_fav, container, false)
        val fragmentAdapter = FavPagerAdapter(
            childFragmentManager,
            getString(R.string.movie_mode),
            getString(R.string.tv_show_mode)
        )
        view.viewPagerFav.adapter = fragmentAdapter
        view.tabLayoutFav.setupWithViewPager(view.viewPagerFav)
        return view
    }


}
