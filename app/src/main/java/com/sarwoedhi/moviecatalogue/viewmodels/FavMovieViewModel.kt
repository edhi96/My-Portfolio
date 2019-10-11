package com.sarwoedhi.moviecatalogue.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.sarwoedhi.moviecatalogue.local.FavDatabase
import com.sarwoedhi.moviecatalogue.local.FavMovieDao
import com.sarwoedhi.moviecatalogue.models.Movie

class FavMovieViewModel(application: Application) : AndroidViewModel(application) {
    private var movieDao: FavMovieDao = FavDatabase.getDatabase(application).movieDao()
    fun selectAllMovies(): LiveData<List<Movie>> {
        return movieDao.selectAll()
    }
}