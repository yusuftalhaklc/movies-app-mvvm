package com.example.moviesapp.model

data class Similar(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)