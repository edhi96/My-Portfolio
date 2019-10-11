package com.example.movieprovider.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.movieprovider.R
import com.example.movieprovider.movie.Movie
import com.example.movieprovider.tvshow.TvShow
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var dataMovie: Movie
    private lateinit var dataTv: TvShow
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        if (intent.getParcelableExtra<Movie>(EXTRA_MOVIE) != null) {
            dataMovie = intent.getParcelableExtra(EXTRA_MOVIE)
            tvDetailTitleMovie.text = dataMovie.title
            tvDetailReleaseMovie.text = dataMovie.release_date
            tvDetailRuntimeMovie.text = dataMovie.runtime
            tvDetailOverviewMovie.text = dataMovie.overview
            Glide.with(this).load("https://image.tmdb.org/t/p/w185/" + dataMovie.posterPath)
                .into(imgDetailsMovie)
            Glide.with(this).load("https://image.tmdb.org/t/p/w500/" + dataMovie.backdropPath)
                .into(bgDetailsMovie)
        } else if (intent.getParcelableExtra<Movie>(EXTRA_TV) != null) {
            dataTv = intent.getParcelableExtra(EXTRA_TV)
            tvDetailTitleMovie.text = dataTv.title
            tvDetailReleaseMovie.text = dataTv.first_air_date
            runtime.text = "Last Air Date"
            tvDetailRuntimeMovie.text = dataTv.last_air_date
            tvDetailOverviewMovie.text = dataTv.overview
            Glide.with(this).load("https://image.tmdb.org/t/p/w185/" + dataTv.posterPath)
                .into(imgDetailsMovie)
            Glide.with(this).load("https://image.tmdb.org/t/p/w500/" + dataTv.backdropPath)
                .into(bgDetailsMovie)
        }

        backBtnMovie.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val EXTRA_MOVIE = "EXTRA_MOVIE"
        const val EXTRA_TV = "EXTRA_TV"
    }
}
