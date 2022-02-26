package com.example.moviesapp.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.makeramen.roundedimageview.RoundedImageView


fun RoundedImageView.downloadFromUrl(url:String) {

    val downloadUrl = "https://image.tmdb.org/t/p/original$url"
    Glide.with(context)
        .load(downloadUrl)
        .error(R.drawable.placeholder)
        .into(this)
}
fun ImageView.downloadFromUrl(url:String) {



    val downloadUrl = "https://image.tmdb.org/t/p/original$url"
    Glide.with(context)
        .load(downloadUrl)
        .error(R.drawable.placeholder)
        .into(this)
}