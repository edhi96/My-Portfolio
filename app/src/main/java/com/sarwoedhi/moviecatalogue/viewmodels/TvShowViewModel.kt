package com.sarwoedhi.moviecatalogue.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sarwoedhi.moviecatalogue.BuildConfig.API_KEY
import com.sarwoedhi.moviecatalogue.models.TvShow
import com.sarwoedhi.moviecatalogue.models.TvShowResponse
import com.sarwoedhi.moviecatalogue.service.ApiClient
import com.sarwoedhi.moviecatalogue.service.ApiInterface
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvShowViewModel : ViewModel() {
    private var mTvShowList: MutableLiveData<ArrayList<TvShow>> = MutableLiveData()

    init {
        loadTvShows()
    }

    fun getTvShows(): LiveData<ArrayList<TvShow>> {
        return mTvShowList
    }

    private fun loadTvShows() {
        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiInterface.getTvShows(API_KEY, "en")
        call.doAsync {
            call.enqueue(object : Callback<TvShowResponse> {
                override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<TvShowResponse>,
                    response: Response<TvShowResponse>
                ) {
                    val body = response.body()
                    if (body != null) {
                        if (!body.results.isNullOrEmpty()) {
                            val tvShows = ArrayList<TvShow>()
                            for (i in body.results.iterator()) {
                                tvShows.add(
                                    TvShow(
                                        i.id,
                                        i.title,
                                        i.overview,
                                        i.first_air_date,
                                        i.posterPath,
                                        i.backdropPath,
                                        i.last_air_date
                                    )
                                )
                            }
                            mTvShowList.postValue(tvShows)
                        }
                    }
                }
            })
        }

    }

}
