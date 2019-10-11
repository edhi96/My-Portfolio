package com.sarwoedhi.moviecatalogue.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sarwoedhi.moviecatalogue.BuildConfig
import com.sarwoedhi.moviecatalogue.local.FavDatabase
import com.sarwoedhi.moviecatalogue.local.FavMovieDao
import com.sarwoedhi.moviecatalogue.models.Movie
import com.sarwoedhi.moviecatalogue.service.ApiClient
import com.sarwoedhi.moviecatalogue.service.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMovieViewModel(application: Application) : AndroidViewModel(application) {
    private var movieDao: FavMovieDao = FavDatabase.getDatabase(application).movieDao()

    private var mMovie: MutableLiveData<Movie>? = null
    private var _progressBarStatus = MutableLiveData<Boolean>()

    val progressBarStatus: LiveData<Boolean>
        get() = _progressBarStatus

    init {
        _progressBarStatus.postValue(true)
    }
    //API

    private fun loadDetailMovie(id: Int) {
        mMovie = MutableLiveData()
        _progressBarStatus.postValue(true)
        val apiInterface: ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiInterface.getDetailMovie(id, BuildConfig.API_KEY)
        call.enqueue(object : Callback<Movie> {
            override fun onFailure(call: Call<Movie>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<Movie>?, response: Response<Movie>?) {
                _progressBarStatus.postValue(false)
                val body = response?.body()
                val movie: Movie?
                if (body != null) {
                    movie = Movie(
                        body.id,
                        body.title,
                        body.overview,
                        body.release_date,
                        body.posterPath,
                        body.backdropPath,
                        body.runtime
                    )
                    mMovie?.postValue(movie)
                }

            }
        })
    }

    fun getDetailMovie(id: Int): LiveData<Movie> {
        if (mMovie == null) {
            loadDetailMovie(id)
        }
        return mMovie as LiveData<Movie>
    }


    //local

    fun insertMovieFav(movie: Movie): Long {
        return movieDao.insertMovieFav(movie)
    }

    fun selectMovieById(id: Int): LiveData<Movie> {
        return movieDao.selectById(id)
    }

    fun deleteMovie(movie: Movie): Int {
        return movieDao.deleteMovie(movie)
    }
}