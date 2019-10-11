package com.sarwoedhi.moviecatalogue.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.sarwoedhi.moviecatalogue.R
import com.sarwoedhi.moviecatalogue.models.Movie
import com.sarwoedhi.moviecatalogue.service.bgImageURL
import com.sarwoedhi.moviecatalogue.service.imageURL
import com.sarwoedhi.moviecatalogue.viewmodels.DetailMovieViewModel
import kotlinx.android.synthetic.main.activity_detail_movie.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class DetailMovieActivity : AppCompatActivity() {
    private lateinit var mDetailMovieViewModel: DetailMovieViewModel
    private var id: Int = 0
    private lateinit var movie: Movie
    private var resultPoster: String? = ""
    private var resultBackDropPoster: String? = ""
    private var isFav = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        mDetailMovieViewModel = ViewModelProviders.of(this).get(DetailMovieViewModel::class.java)
        if (intent.getIntExtra(EXTRA_MOVIE, 0) != 0) {
            id = intent.getIntExtra(EXTRA_MOVIE, 0)
            mDetailMovieViewModel.getDetailMovie(id).observe(this, getDetail)
        }
        mDetailMovieViewModel.progressBarStatus.observe(this, Observer { isShowing ->
            when (isShowing) {
                true -> progressDetail.visibility = View.VISIBLE
                else -> progressDetail.visibility = View.GONE
            }
        })
        backBtnMovie.setOnClickListener {
            finish()
        }
        mDetailMovieViewModel.selectMovieById(id).observe(this, Observer { movie ->
            if (movie != null) {
                isFav = true
                favDetailStatus.setImageDrawable(getDrawable(R.drawable.ic_favorite_add_24dp))
            } else {
                isFav = false
                favDetailStatus.setImageDrawable(getDrawable(R.drawable.ic_favorite_remove_24dp))
            }

        })

        favDetailStatus.setOnClickListener {
            movie = Movie(
                id,
                tvDetailTitleMovie.text.toString(),
                tvDetailOverviewMovie.text.toString(),
                tvDetailReleaseMovie.text.toString(),
                resultPoster,
                resultBackDropPoster,
                tvDetailRuntimeMovie.text.toString()
            )
            if (isFav) {
                doAsync {
                    val delFav = mDetailMovieViewModel.deleteMovie(movie)
                    uiThread {
                        if (delFav > -1) {
                            favDetailStatus.setImageDrawable(
                                ContextCompat.getDrawable(
                                    this@DetailMovieActivity,
                                    R.drawable.ic_favorite_remove_24dp
                                )
                            )
                            toast(getString(R.string.success_del_fav))
                            isFav = false
                        }
                    }
                }
            } else if (!isFav) {
                doAsync {
                    val addFav = mDetailMovieViewModel.insertMovieFav(movie)
                    uiThread {
                        if (addFav != (-1).toLong()) {
                            favDetailStatus.setImageDrawable(
                                ContextCompat.getDrawable(
                                    this@DetailMovieActivity,
                                    R.drawable.ic_favorite_add_24dp
                                )
                            )
                            toast(getString(R.string.success_add_fav))
                            isFav = true
                        }
                    }
                }
            }
        }

    }

    private val getDetail = Observer<Movie> { detailsMovie ->

        if (detailsMovie != null) {
            with(detailsMovie)
            {

                tvDetailTitleMovie.text = title
                tvDetailOverviewMovie.text = overview
                tvDetailReleaseMovie.text = release_date
                tvDetailRuntimeMovie.text = runtime
                resultPoster = posterPath
                resultBackDropPoster = backdropPath
                Glide.with(this@DetailMovieActivity).load(imageURL + resultPoster)
                    .into(imgDetailsMovie)
                Glide.with(this@DetailMovieActivity).load(bgImageURL + resultBackDropPoster)
                    .into(bgDetailsMovie)
            }
        }
    }

    companion object {
        const val EXTRA_MOVIE = "EXTRA_MOVIE"
        const val EXTRA_RELEASE = "EXTRA_RELEASE"
    }
}
