package com.sarwoedhi.moviecatalogue.local

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.sarwoedhi.moviecatalogue.models.TvShow

@Dao
interface FavTvShowDao {
    @Insert(onConflict = REPLACE)
    fun insertTvShowFav(data: TvShow): Long

    @Query("SELECT * FROM TvShow")
    fun selectAllTvShow(): LiveData<List<TvShow>>

    @Query("SELECT * FROM TvShow WHERE id == :id")
    fun selectTvById(id: Int): LiveData<TvShow>

    @Delete
    fun deleteTv(tv: TvShow): Int

    @Query("SELECT * FROM TvShow")
    fun selectAllTvCursor(): Cursor
}