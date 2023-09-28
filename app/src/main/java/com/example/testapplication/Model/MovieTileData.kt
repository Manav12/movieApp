package com.example.testapplication.Model

data class MovieTileData(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)