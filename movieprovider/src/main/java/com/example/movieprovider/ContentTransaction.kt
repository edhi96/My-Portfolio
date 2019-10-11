package com.example.movieprovider

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import com.example.movieprovider.movie.Movie
import com.example.movieprovider.tvshow.TvShow

class ContentTransaction(context: Context) {

        private val myContentResolver: ContentResolver = context.contentResolver

        fun selectAllMovie(): ArrayList<Movie> {
            val movieList: ArrayList<Movie> = ArrayList()
            val cursor: Cursor? = myContentResolver.query(
                ContentDB.MyContentProviderURI.Content_URI_MOVIE, null, null, null, null)
            var movie: Movie
            cursor?.let {
                if (it.moveToFirst()) {
                    do {
                        movie = Movie(
                            it.getString(it.getColumnIndexOrThrow(ContentDB.MovieTable.id)).toInt(),
                            it.getString(it.getColumnIndexOrThrow(ContentDB.MovieTable.title)),
                            it.getString(it.getColumnIndexOrThrow(ContentDB.MovieTable.overview)),
                            it.getString(it.getColumnIndexOrThrow(ContentDB.MovieTable.release_date)),
                            it.getString(it.getColumnIndexOrThrow(ContentDB.MovieTable.poster_path)),
                            it.getString(it.getColumnIndexOrThrow(ContentDB.MovieTable.backdrop_path)),
                            it.getString(it.getColumnIndexOrThrow(ContentDB.MovieTable.runtime))
                        )
                        movieList.add(movie)
                    } while (cursor.moveToNext())
                }
                cursor.close()
            }
            return movieList
        }


    fun selectAllTvShow(): ArrayList<TvShow> {
        val tvList: ArrayList<TvShow> = ArrayList()
        val cursor: Cursor? = myContentResolver.query(
            ContentDB.MyContentProviderURI.Content_URI_TV, null, null, null, null)
        var tvShow: TvShow
        cursor?.let {
            if (it.moveToFirst()) {
                do {
                    tvShow = TvShow(
                        it.getString(it.getColumnIndexOrThrow(ContentDB.TvTable.id)).toInt(),
                        it.getString(it.getColumnIndexOrThrow(ContentDB.TvTable.title)),
                        it.getString(it.getColumnIndexOrThrow(ContentDB.TvTable.overview)),
                        it.getString(it.getColumnIndexOrThrow(ContentDB.TvTable.first_air_date)),
                        it.getString(it.getColumnIndexOrThrow(ContentDB.TvTable.posterPath)),
                        it.getString(it.getColumnIndexOrThrow(ContentDB.TvTable.backdropPath)),
                        it.getString(it.getColumnIndexOrThrow(ContentDB.TvTable.last_air_date))
                    )
                    tvList.add(tvShow)
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        return tvList
    }
}