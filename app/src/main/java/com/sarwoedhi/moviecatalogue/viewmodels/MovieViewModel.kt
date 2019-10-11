package com.sarwoedhi.moviecatalogue.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sarwoedhi.moviecatalogue.BuildConfig
import com.sarwoedhi.moviecatalogue.models.Movie
import com.sarwoedhi.moviecatalogue.models.MovieResponse
import com.sarwoedhi.moviecatalogue.service.ApiClient
import com.sarwoedhi.moviecatalogue.service.ApiInterface
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel : ViewModel() {

    private var mMovieList: MutableLiveData<ArrayList<Movie>> = MutableLiveData()

    init {
        loadMovies()
    }

    fun getMovies(): LiveData<ArrayList<Movie>> {
        return mMovieList
    }

    private fun loadMovies() {
        val apiInterface: ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val call: Call<MovieResponse> = apiInterface.getMovies(BuildConfig.API_KEY, "en")
        val movies: ArrayList<Movie> = ArrayList()
        call.doAsync {
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
                            mMovieList.postValue(movies)
                        }
                    }
                }
            })
        }
    }
}