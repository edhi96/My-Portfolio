package com.sarwoedhi.moviecatalogue.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sarwoedhi.moviecatalogue.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                val fragment: Fragment?
                when (item.itemId) {
                    R.id.navigation_movie -> {
                        setActionBarTitle(getString(R.string.movie_list))
                        fragment = MovieFragment()
                        loadFragment(fragment)
                        return true
                    }
                    R.id.navigation_tv_show -> {
                        setActionBarTitle(getString(R.string.tv_show_list))
                        fragment = TvShowFragment()
                        loadFragment(fragment)
                        return true
                    }
                    R.id.navigation_fav -> {
                        setActionBarTitle(getString(R.string.fav))
                        fragment = FavFragment()
                        loadFragment(fragment)
                    }
                }
                return false
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nav_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        nav_view.selectedItemId = R.id.navigation_movie
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.action_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_setting -> {
                startActivity(Intent(applicationContext, SettingsActivity::class.java))
                true
            }
            else -> true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_layout, fragment, fragment.javaClass.simpleName).commit()
    }

    private fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }
}
