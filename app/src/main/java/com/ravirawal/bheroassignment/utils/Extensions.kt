package com.ravirawal.bheroassignment.utils

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ravirawal.bheroassignment.R

fun ImageView.loadImage(url: String?) {
    Glide.with(this)
        .load(url ?: "")
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .fitCenter()
        .placeholder(R.drawable.ic_beer)
        .error(R.drawable.ic_beer)
        .into(this)
}

fun ImageView.load(@DrawableRes drawableRes: Int) {
    Glide.with(this).load(drawableRes).fitCenter().into(this)
}

fun String?.default(default: String? = null): String {
    if (this.isNullOrEmpty()) {
        return default ?: ""
    }
    return this
}

inline fun <T : Any> T?.act(f: (it: T) -> Unit) {
    if (this != null) f(this)
}