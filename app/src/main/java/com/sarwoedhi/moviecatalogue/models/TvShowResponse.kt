package com.sarwoedhi.moviecatalogue.models

data class TvShowResponse(
    var page: Int,
    val totalResult: Int,
    val totalPage: Int,
    val results: ArrayList<TvShow>
)