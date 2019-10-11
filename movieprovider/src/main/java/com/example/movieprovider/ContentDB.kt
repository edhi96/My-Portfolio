package com.example.movieprovider

import android.net.Uri
import android.provider.BaseColumns

object ContentDB {
    class MovieTable : BaseColumns {
        companion object {
            const val TABLE_MOVIE = "Movie"
            const val id = "id"
            const val title: String = "title"
            const val overview: String = "overview"
            const val release_date: String = "release_date"
            const val poster_path: String = "posterPath"
            const val backdrop_path: String = "backdropPath"
            const val runtime: String = "runtime"
        }
    }

    class TvTable : BaseColumns {
        companion object {
            const val TABLE_TV = "TvShow"
            const val id = "id"
            const val title: String = "title"
            const val overview: String = "overview"
            const val first_air_date: String = "first_air_date"
            const val posterPath: String = "posterPath"
            const val backdropPath: String = "backdropPath"
            const val last_air_date: String = "last_air_date"
        }
    }

    class MyContentProviderURI {
        companion object {
            private const val AUTHORITY = "com.sarwoedhi.moviecatalogue.provider.MovieProvider"
            private const val MOVIE_TABLE = MovieTable.TABLE_MOVIE
            private const val TV_TABLE = TvTable.TABLE_TV
            val Content_URI_MOVIE: Uri = Uri.parse("content://$AUTHORITY/$MOVIE_TABLE")
            val Content_URI_TV: Uri = Uri.parse("content://$AUTHORITY/$TV_TABLE")
        }
    }
}