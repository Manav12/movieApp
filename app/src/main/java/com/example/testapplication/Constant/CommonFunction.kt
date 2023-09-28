package com.example.testapplication.Constant

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter

fun ImageView.loadImageUrl(imageUrl: String) {
    Glide.with(this)
        .load(imageUrl)
        .transform(FitCenter())
        .into(this)
}