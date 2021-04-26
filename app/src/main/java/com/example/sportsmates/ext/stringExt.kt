package com.example.sportsmates.ext

import android.text.format.DateUtils
import android.util.Log
import java.time.Instant


fun String.convertToAgoFormat(): CharSequence? {
    Log.v("time", DateUtils.getRelativeTimeSpanString(Instant.parse(this).toEpochMilli()).toString())
    return DateUtils.getRelativeTimeSpanString(Instant.parse(this).toEpochMilli())
}