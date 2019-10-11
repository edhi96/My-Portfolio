package com.sarwoedhi.moviecatalogue.local

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.sarwoedhi.moviecatalogue.models.Movie

@Dao
interface FavMovieDao {
    @Insert(onConflict = REPLACE)
    fun insertMovieFav(data: Movie): Long

    @Query("SELECT * FROM Movie")
    fun selectAll(): LiveData<List<Movie>>

    @Query("SELECT * FROM  Movie")
    fun selectMoviesFromWidget(): List<Movie>

    @Query("SELECT * FROM Movie WHERE id == :id")
    fun selectById(id: Int): LiveData<Movie>

    @Delete
    fun deleteMovie(movie: Movie): Int

    @Query("SELECT * FROM Movie")
    fun selectAllMovieCursor(): Cursor
}