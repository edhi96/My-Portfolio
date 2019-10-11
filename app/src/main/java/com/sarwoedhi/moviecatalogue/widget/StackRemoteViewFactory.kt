package com.sarwoedhi.moviecatalogue.widget

import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.sarwoedhi.moviecatalogue.R
import com.sarwoedhi.moviecatalogue.local.FavDatabase
import com.sarwoedhi.moviecatalogue.models.Movie
import com.sarwoedhi.moviecatalogue.widget.FavMovieWidget.Companion.EXTRA_ITEM

class StackRemoteViewFactory(val context: Context) : RemoteViewsService.RemoteViewsFactory {

    private val mItem = ArrayList<Movie>()

    override fun onCreate() {
        context.let {
            mItem.addAll(FavDatabase.getDatabase(it).movieDao().selectMoviesFromWidget())
        }
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun onDataSetChanged() {

        val identityToken = Binder.clearCallingIdentity()

        context.let {
            mItem.clear()
            mItem.addAll(FavDatabase.getDatabase(it).movieDao().selectMoviesFromWidget())
        }
        Binder.restoreCallingIdentity(identityToken)
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(context.packageName, R.layout.widget_item)
        val itemPoster = Glide.with(context).asBitmap()
            .load("https://image.tmdb.org/t/p/w185" + mItem[position].posterPath)
            .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get()

        rv.setImageViewBitmap(R.id.imgWidgetItem, itemPoster)
        val extras = Bundle()
        extras.putInt(EXTRA_ITEM, position)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.imgWidgetItem, fillInIntent)
        return rv
    }

    override fun getCount(): Int {
        return mItem.size
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun onDestroy() {

    }

}