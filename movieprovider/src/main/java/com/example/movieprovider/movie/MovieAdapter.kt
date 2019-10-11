package com.example.movieprovider.movie

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieprovider.detail.DetailActivity
import com.example.movieprovider.detail.DetailActivity.Companion.EXTRA_MOVIE
import com.example.movieprovider.R
import kotlinx.android.synthetic.main.item_movie.view.*
const val imageURL = "https://image.tmdb.org/t/p/w185/"
class MovieAdapter(private val context: Context) : RecyclerView.Adapter<MovieAdapter.CardViewViewHolder>() {
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
            val dataMovieIntent = Intent(context, DetailActivity::class.java)
             dataMovieIntent.putExtra(EXTRA_MOVIE, listMovie[p1])
            context.startActivity(dataMovieIntent)
            Toast.makeText(
                context,
                "${context.getString(R.string.choose)} ${listMovie[p1].title}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    class CardViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            itemView.txtNameMovie.text = movie.title
            itemView.tvMovieRelease.text = movie.release_date
            Glide.with(itemView.context).load(imageURL +movie.posterPath).into(itemView.imgPhotoMovie)
        }
    }
}