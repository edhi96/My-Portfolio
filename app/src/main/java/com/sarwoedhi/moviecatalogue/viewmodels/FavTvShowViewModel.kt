package com.sarwoedhi.moviecatalogue.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.sarwoedhi.moviecatalogue.local.FavDatabase
import com.sarwoedhi.moviecatalogue.local.FavTvShowDao
import com.sarwoedhi.moviecatalogue.models.TvShow

class FavTvShowViewModel(application: Application) : AndroidViewModel(application) {
    var tvShowDao: FavTvShowDao = FavDatabase.getDatabase(application).tvShowDao()
    fun selectAllTvShow(): LiveData<List<TvShow>> {
        return tvShowDao.selectAllTvShow()
    }
}