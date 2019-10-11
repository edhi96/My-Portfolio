package com.sarwoedhi.moviecatalogue.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class TvShow(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val title: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("first_air_date") val first_air_date: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("last_air_date") val last_air_date: String?
)