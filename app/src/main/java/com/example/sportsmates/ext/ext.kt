package com.example.sportsmates.ext
import android.view.View
import android.widget.ProgressBar
import com.example.sportsmates.R
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.gson.Gson


fun stopShimmer(shimmer:ShimmerFrameLayout){
    shimmer.stopShimmer()
    shimmer.visibility=View.GONE
}

fun View.showLoading() {
    val progress = this.findViewById<ProgressBar>(R.id.progress_circular)
    progress.visibility = View.VISIBLE
}

fun View.hideLoading() {
    val progress = this.findViewById<ProgressBar>(R.id.progress_circular)
    progress.visibility = View.GONE
}

inline fun <reified R> String.fromJson(): R {
    return Gson().fromJson(this, R::class.java)
}

inline fun <reified R> R.toJson(): String {
    return Gson().toJson(this, R::class.java)
}
