package com.sarwoedhi.moviecatalogue.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sarwoedhi.moviecatalogue.BuildConfig
import com.sarwoedhi.moviecatalogue.local.FavDatabase
import com.sarwoedhi.moviecatalogue.local.FavTvShowDao
import com.sarwoedhi.moviecatalogue.models.TvShow
import com.sarwoedhi.moviecatalogue.service.ApiClient
import com.sarwoedhi.moviecatalogue.service.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailTvShowViewModel(application: Application) : AndroidViewModel(application) {
    private var tvShowDao: FavTvShowDao = FavDatabase.getDatabase(application).tvShowDao()

    private var mTvShow: MutableLiveData<TvShow>? = null
    private var _progressBarStatus = MutableLiveData<Boolean>()
    val progressBarStatus: LiveData<Boolean>
        get() = _progressBarStatus

    init {
        _progressBarStatus.postValue(true)
    }

    fun getDetailTv(id: Int): LiveData<TvShow> {
        if (mTvShow == null) {
            loadDetailTv(id)
        }
        return mTvShow as LiveData<TvShow>
    }

    private fun loadDetailTv(id: Int) {
        mTvShow = MutableLiveData()
        _progressBarStatus.postValue(true)
        val apiInterface: ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiInterface.getDetailTv(id, BuildConfig.API_KEY)
        call.enqueue(object : Callback<TvShow> {
            override fun onFailure(call: Call<TvShow>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<TvShow>?, response: Response<TvShow>?) {
                _progressBarStatus.postValue(false)
                val body = response?.body()
                val detailTv: TvShow?
                if (body != null) {
                    detailTv = TvShow(
                        body.id,
                        body.title,
                        body.overview,
                        body.first_air_date,
                        body.posterPath,
                        body.backdropPath,
                        body.last_air_date
                    )
                    mTvShow?.postValue(detailTv)
                }

            }
        })
    }

    //local
    fun insertTvShowFav(tv: TvShow): Long {
        return tvShowDao.insertTvShowFav(tv)
    }

    fun selectTvShowById(id: Int): LiveData<TvShow> {
        return tvShowDao.selectTvById(id)
    }

    fun deleteTvShow(tv: TvShow): Int {
        return tvShowDao.deleteTv(tv)
    }
}