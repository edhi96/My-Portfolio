package com.sarwoedhi.moviecatalogue.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sarwoedhi.moviecatalogue.BuildConfig
import com.sarwoedhi.moviecatalogue.models.Movie
import com.sarwoedhi.moviecatalogue.models.MovieResponse
import com.sarwoedhi.moviecatalogue.models.TvShow
import com.sarwoedhi.moviecatalogue.models.TvShowResponse
import com.sarwoedhi.moviecatalogue.service.ApiClient
import com.sarwoedhi.moviecatalogue.service.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {
    private var mResultMovieList: MutableLiveData<ArrayList<Movie>> = MutableLiveData()
    private var mResultTvShowList: MutableLiveData<ArrayList<TvShow>> = MutableLiveData()

    fun getResultSearchMovies(query: String): LiveData<ArrayList<Movie>> {
        resultSearchMovies(query = query)
        return mResultMovieList
    }

    private fun resultSearchMovies(query: String): LiveData<ArrayList<Movie>> {
        val apiInterface: ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val call: Call<MovieResponse> =
            apiInterface.getSearchMovies(BuildConfig.API_KEY, "en", query)
        val movies: ArrayList<Movie> = ArrayList()
        call.enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>?, t: Throwable?) {
            }

            override fun onResponse(
                call: Call<MovieResponse>?,
                response: Response<MovieResponse>?
            ) {
                val body = response?.body()
                if (body != null) {

                    if (!body.results.isNullOrEmpty()) {
                        for (i in body.results.iterator()) {
                            movies.add(
                                Movie(
                                    i.id,
                                    i.title,
                                    i.overview,
                                    i.release_date,
                                    i.posterPath,
                                    i.backdropPath,
                                    i.runtime
                                )
                            )
                        }
                        mResultMovieList.postValue(movies)
                    } else {
                        mResultMovieList.postValue(null)
                    }
                }
            }
        })
        return mResultMovieList
    }

    private fun resultSearchTvShows(query: String): LiveData<ArrayList<TvShow>> {
        val apiInterface: ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val call: Call<TvShowResponse> =
            apiInterface.getSearchTVShows(BuildConfig.API_KEY, "en", query)
        val tvShow: ArrayList<TvShow> = ArrayList()
        call.enqueue(object : Callback<TvShowResponse> {
            override fun onResponse(
                call: Call<TvShowResponse>?,
                response: Response<TvShowResponse>?
            ) {
                val body = response?.body()
                if (body != null) {
                    if (!body.results.isNullOrEmpty()) {
                        for (i in body.results.iterator()) {
                            tvShow.add(
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
                        mResultTvShowList.postValue(tvShow)
                    } else {
                        mResultTvShowList.postValue(null)
                    }
                }
            }

            override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {

            }
        })
        return mResultTvShowList
    }

    fun getResultSearchTvShows(query: String): LiveData<ArrayList<TvShow>> {
        resultSearchTvShows(query = query)
        return mResultTvShowList
    }
}