package com.sarwoedhi.moviecatalogue.ui

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.sarwoedhi.moviecatalogue.R
import com.sarwoedhi.moviecatalogue.models.TvShow
import com.sarwoedhi.moviecatalogue.service.imageURL
import com.sarwoedhi.moviecatalogue.viewmodels.DetailTvShowViewModel
import kotlinx.android.synthetic.main.activity_detail_tv_show.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class DetailTvShowActivity : AppCompatActivity() {
    private lateinit var mDetailTvViewModel: DetailTvShowViewModel
    var id: Int = 0
    private lateinit var tvShow: TvShow
    private var resultPoster: String? = null
    private var resultBackDropPoster: String? = null
    private var isFav = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tv_show)
        mDetailTvViewModel = ViewModelProviders.of(this).get(DetailTvShowViewModel::class.java)
        if (intent.getIntExtra(EXTRA_TV, 0) != 0) {
            id = intent.getIntExtra(EXTRA_TV, 0)
            mDetailTvViewModel.getDetailTv(id).observe(this, getDetail)
        }
        mDetailTvViewModel.progressBarStatus.observe(this, Observer { isShowing ->
            when (isShowing) {
                true -> progressDetailTv.visibility = VISIBLE
                else -> progressDetailTv.visibility = GONE
            }
        })
        backBtnTv.setOnClickListener {
            finish()
        }
        mDetailTvViewModel.selectTvShowById(id).observe(this, Observer { tvShow ->
            isFav = if (tvShow != null) {
                favDetailStatusTv.setImageDrawable(getDrawable(R.drawable.ic_favorite_add_24dp))
                true
            } else {
                favDetailStatusTv.setImageDrawable(getDrawable(R.drawable.ic_favorite_remove_24dp))
                false
            }
        })

        favDetailStatusTv.setOnClickListener {
            tvShow = TvShow(
                id,
                tvDetailTitleTv.text.toString(),
                tvDetailOverviewTv.text.toString(),
                tvDetailReleaseTv.text.toString(),
                resultPoster,
                resultBackDropPoster,
                tvDetailLastAirDateTv.text.toString()
            )
            if (isFav) {
                doAsync {
                    val delTvShowFav = mDetailTvViewModel.deleteTvShow(tvShow)
                    uiThread {
                        if (delTvShowFav != -1) {
                            favDetailStatusTv.setImageDrawable(
                                ContextCompat.getDrawable(
                                    this@DetailTvShowActivity,
                                    R.drawable.ic_favorite_remove_24dp
                                )
                            )
                            toast(getString(R.string.success_del_fav))
                            isFav = false
                        }
                    }
                }
            } else {
                doAsync {
                    val addTvShowFav = mDetailTvViewModel.insertTvShowFav(tvShow)
                    uiThread {
                        if (addTvShowFav != (-1).toLong()) {
                            favDetailStatusTv.setImageDrawable(
                                ContextCompat.getDrawable(
                                    this@DetailTvShowActivity,
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

    private val getDetail = Observer<TvShow> { detailsTvShow ->
        if (detailsTvShow != null) {
            with(detailsTvShow)
            {

                tvDetailTitleTv.text = title
                tvDetailOverviewTv.text = overview
                tvDetailReleaseTv.text = first_air_date
                tvDetailLastAirDateTv.text = last_air_date
                resultPoster = posterPath
                resultBackDropPoster = backdropPath
                Glide.with(this@DetailTvShowActivity).load(imageURL + resultPoster)
                    .into(imgDetailsTv)
                Glide.with(this@DetailTvShowActivity).load(imageURL + resultBackDropPoster)
                    .into(bgDetailsTv)
            }
        }
    }

    companion object {
        const val EXTRA_TV = "EXTRA_TV"
    }
}