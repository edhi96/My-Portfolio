package com.sarwoedhi.moviecatalogue.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sarwoedhi.moviecatalogue.R
import com.sarwoedhi.moviecatalogue.models.TvShow
import com.sarwoedhi.moviecatalogue.service.imageURL
import com.sarwoedhi.moviecatalogue.ui.DetailTvShowActivity
import com.sarwoedhi.moviecatalogue.ui.DetailTvShowActivity.Companion.EXTRA_TV
import kotlinx.android.synthetic.main.item_tvshow.view.*

class TvShowAdapter(private val context: Context) :
    RecyclerView.Adapter<TvShowAdapter.CardViewViewHolder>() {
    private var lstTvShow: ArrayList<TvShow> = ArrayList()
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CardViewViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_tvshow, p0, false)
        return CardViewViewHolder(view)
    }

    fun setData(items: ArrayList<TvShow>) {
        lstTvShow.clear()
        lstTvShow.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return lstTvShow.size
    }

    override fun onBindViewHolder(p0: CardViewViewHolder, p1: Int) {
        p0.bind(lstTvShow[p1])
        p0.itemView.setOnClickListener {
            val dataTvShowIntent = Intent(context, DetailTvShowActivity::class.java)
            dataTvShowIntent.putExtra(EXTRA_TV, lstTvShow[p1].id)
            context.startActivity(dataTvShowIntent)
            Toast.makeText(
                context,
                "${context.getString(R.string.choose)} ${lstTvShow[p1].title}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    class CardViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tvShow: TvShow) {
            itemView.tvNameTvShow.text = tvShow.title
            itemView.txtTvShowRelease.text = tvShow.first_air_date
            Glide.with(itemView.context).load(imageURL + tvShow.posterPath)
                .into(itemView.imgPhotoTvShow)
        }
    }
}