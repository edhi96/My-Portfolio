package com.sarwoedhi.moviecatalogue.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class Movie(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id") var id: Int?,
    @SerializedName("original_title") var title: String?,
    @SerializedName("overview") var overview: String?,
    @SerializedName("release_date") var release_date: String?,
    @SerializedName("poster_path") var posterPath: String?,
    @SerializedName("backdrop_path") var backdropPath: String?,
    @SerializedName("runtime") var runtime: String?
)