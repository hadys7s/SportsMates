package com.example.sportsmates.ext
import android.view.View
import android.widget.ProgressBar
import com.example.sportsmates.R
import com.facebook.shimmer.ShimmerFrameLayout


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
