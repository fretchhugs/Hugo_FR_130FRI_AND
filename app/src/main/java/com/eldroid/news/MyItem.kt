package com.eldroid.news

import android.os.Parcel
import android.os.Parcelable

data class NewsItems(val title: String, val description: String, val imageResId: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeInt(imageResId)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<NewsItems> {
        override fun createFromParcel(parcel: Parcel): NewsItems {
            return NewsItems(parcel)
        }

        override fun newArray(size: Int): Array<NewsItems?> {
            return arrayOfNulls(size)
        }
    }
}