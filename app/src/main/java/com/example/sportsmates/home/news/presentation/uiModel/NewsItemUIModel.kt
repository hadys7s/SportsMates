package com.example.sportsmates.home.news.presentation.uiModel

import android.os.Parcel
import android.os.Parcelable

class NewsItemUIModel(
    val website: String?,
    val title: String?,
    val imageUrl: String?,
    val publishedAt: String?,
    val content: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(website)
        parcel.writeString(title)
        parcel.writeString(imageUrl)
        parcel.writeString(publishedAt)
        parcel.writeString(content)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewsItemUIModel> {
        override fun createFromParcel(parcel: Parcel): NewsItemUIModel {
            return NewsItemUIModel(parcel)
        }

        override fun newArray(size: Int): Array<NewsItemUIModel?> {
            return arrayOfNulls(size)
        }
    }
}