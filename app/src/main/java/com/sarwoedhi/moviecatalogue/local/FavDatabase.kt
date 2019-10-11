package com.sarwoedhi.moviecatalogue.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sarwoedhi.moviecatalogue.models.Movie
import com.sarwoedhi.moviecatalogue.models.TvShow

@Database(entities = [(Movie::class), (TvShow::class)], version = 1)
abstract class FavDatabase : RoomDatabase() {
    abstract fun movieDao(): FavMovieDao
    abstract fun tvShowDao(): FavTvShowDao

    companion object {
        @Volatile
        private lateinit var INSTANCE: FavDatabase

        @Synchronized
        fun getDatabase(context: Context): FavDatabase {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                FavDatabase::class.java,
                "fav_database"
            )
                .allowMainThreadQueries()
                .build()
            return INSTANCE
        }
    }
}