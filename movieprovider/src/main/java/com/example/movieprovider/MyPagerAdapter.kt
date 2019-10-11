package com.example.movieprovider

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.movieprovider.tvshow.TvShowFragment
import com.example.movieprovider.movie.MovieFragment

class MyPagerAdapter(fm: FragmentManager, private val movieMode: String, private val tvMode: String) : FragmentPagerAdapter(fm) {
    private val pagesList = listOf(MovieFragment(),
        TvShowFragment()
    )
    override fun getItem(p0: Int): Fragment {
        return pagesList[p0]
    }

    override fun getCount(): Int {
        return pagesList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> movieMode
            else -> return tvMode
        }
    }

}