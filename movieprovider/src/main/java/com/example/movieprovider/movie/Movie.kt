package com.example.movieprovider.movie

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
@Parcelize
data class Movie(
    @SerializedName("id") var id: Int?,
    @SerializedName("original_title") var title: String?,
    @SerializedName("overview") var overview: String?,
    @SerializedName("release_date") var release_date: String?,
    @SerializedName("poster_path") var posterPath: String?,
    @SerializedName("backdrop_path") var backdropPath: String?,
    @SerializedName("runtime") var runtime: String?
):Parcelable