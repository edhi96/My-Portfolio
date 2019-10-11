package com.sarwoedhi.moviecatalogue.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sarwoedhi.moviecatalogue.R
import com.sarwoedhi.moviecatalogue.models.Movie
import com.sarwoedhi.moviecatalogue.service.imageURL
import com.sarwoedhi.moviecatalogue.ui.DetailMovieActivity
import com.sarwoedhi.moviecatalogue.ui.DetailMovieActivity.Companion.EXTRA_MOVIE
import com.sarwoedhi.moviecatalogue.ui.DetailMovieActivity.Companion.EXTRA_RELEASE
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(private val context: Context) :
    RecyclerView.Adapter<MovieAdapter.CardViewViewHolder>() {
    private var listMovie: ArrayList<Movie> = ArrayList()
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CardViewViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_movie, p0, false)
        return CardViewViewHolder(view)
    }

    fun setData(items: ArrayList<Movie>) {
        listMovie.clear()
        listMovie.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listMovie.size
    }

    override fun onBindViewHolder(p0: CardViewViewHolder, p1: Int) {
        p0.bind(listMovie[p1])
        p0.itemView.setOnClickListener {
            val dataMovieIntent = Intent(context, DetailMovieActivity::class.java)
            dataMovieIntent.putExtra(EXTRA_MOVIE, listMovie[p1].id)
            dataMovieIntent.putExtra(EXTRA_RELEASE, listMovie[p1].release_date)
            context.startActivity(dataMovieIntent)
        }
    }


    class CardViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            itemView.txtNameMovie.text = movie.title
            itemView.tvMovieRelease.text = movie.release_date
            Glide.with(itemView.context).load(imageURL + movie.posterPath)
                .into(itemView.imgPhotoMovie)
        }
    }
}
