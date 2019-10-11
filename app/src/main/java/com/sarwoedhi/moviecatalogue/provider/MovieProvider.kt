package com.sarwoedhi.moviecatalogue.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.sarwoedhi.moviecatalogue.local.FavDatabase
import com.sarwoedhi.moviecatalogue.models.Movie
import com.sarwoedhi.moviecatalogue.models.TvShow

class MovieProvider : ContentProvider() {

    private val authLoc = "com.sarwoedhi.moviecatalogue.provider.MovieProvider"
    private val movieTable = Movie::class.java.simpleName
    private val tvShowTable = TvShow::class.java.simpleName
    private var uriMatcher: UriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    init {
        uriMatcher.addURI(authLoc, movieTable, 1)
        uriMatcher.addURI(authLoc, tvShowTable, 2)
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            1 -> {
                context?.let { FavDatabase.getDatabase(it).movieDao().selectAllMovieCursor() }
            }
            2 -> {
                context?.let { FavDatabase.getDatabase(it).tvShowDao().selectAllTvCursor() }
            }
            else -> null
        }
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        throw IllegalArgumentException("Failed ")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        throw IllegalArgumentException("Failed ")
    }

    override fun getType(uri: Uri): String? {
        throw IllegalArgumentException("Failed")
    }
}