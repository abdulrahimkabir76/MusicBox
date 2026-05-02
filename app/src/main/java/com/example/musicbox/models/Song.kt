package com.example.musicbox.models

import android.os.Parcel
import android.os.Parcelable

data class Song(
    val id: String,
    val title: String,
    val artist: String,
    val cardColor: Int
) : Parcelable {
    private constructor(parcel: Parcel) : this(
        id = parcel.readString().orEmpty(),
        title = parcel.readString().orEmpty(),
        artist = parcel.readString().orEmpty(),
        cardColor = parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(artist)
        parcel.writeInt(cardColor)
    }

    override fun describeContents(): Int = 0

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Song> = object : Parcelable.Creator<Song> {
            override fun createFromParcel(parcel: Parcel): Song = Song(parcel)

            override fun newArray(size: Int): Array<Song?> = arrayOfNulls(size)
        }
    }
}

