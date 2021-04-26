package com.example.sportsmates.ext
import android.view.View
import com.facebook.shimmer.ShimmerFrameLayout


fun stopShimmer(shimmer:ShimmerFrameLayout){
    shimmer.stopShimmer()
    shimmer.visibility=View.GONE
}
