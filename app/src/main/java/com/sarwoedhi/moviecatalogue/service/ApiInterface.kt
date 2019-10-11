package com.sarwoedhi.moviecatalogue.service

import com.sarwoedhi.moviecatalogue.models.Movie
import com.sarwoedhi.moviecatalogue.models.MovieResponse
import com.sarwoedhi.moviecatalogue.models.TvShow
import com.sarwoedhi.moviecatalogue.models.TvShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("discover/movie?")
    fun getMovies(@Query("api_key") apiKey: String, @Query("language=en") query: String): Call<MovieResponse>

    @GET("movie/{id}" + "?")
    fun getDetailMovie(@Path("id") id: Int, @Query("api_key") query: String): Call<Movie>

    @GET("tv/{id}" + "?")
    fun getDetailTv(@Path("id") id: Int, @Query("api_key") query: String): Call<TvShow>

    @GET("discover/movie?")
    fun getReleaseToday(
        @Query("api_key") apiKey: String, @Query("primary_release_date.gte") releaseDateGte: String, @Query(
            "primary_release_date.lte"
        ) releaseDateLte: String
    ): Call<MovieResponse>

    @GET("discover/tv?")
    fun getTvShows(@Query("api_key") apiKey: String, @Query("language=") query: String): Call<TvShowResponse>

    @GET("search/movie?")
    fun getSearchMovies(
        @Query("api_key") apiKey: String,
        @Query("language=en") languages: String,
        @Query("query") searchQuery: String
    ): Call<MovieResponse>

    @GET("search/tv?")
    fun getSearchTVShows(
        @Query("api_key") apiKey: String,
        @Query("language=en") languages: String,
        @Query("query") searchQuery: String
    ): Call<TvShowResponse>

}