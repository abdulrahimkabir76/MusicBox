package com.example.musicbox.models

import android.os.Parcel
import android.os.Parcelable

data class Playlist(
    val id: String,
    val name: String,
    val songs: List<Song>
) : Parcelable {
    private constructor(parcel: Parcel) : this(
        id = parcel.readString().orEmpty(),
        name = parcel.readString().orEmpty(),
        songs = mutableListOf<Song>().also { parcel.readTypedList(it, Song.CREATOR) }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeTypedList(songs)
    }

    override fun describeContents(): Int = 0

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Playlist> = object : Parcelable.Creator<Playlist> {
            override fun createFromParcel(parcel: Parcel): Playlist = Playlist(parcel)

            override fun newArray(size: Int): Array<Playlist?> = arrayOfNulls(size)
        }
    }
}

