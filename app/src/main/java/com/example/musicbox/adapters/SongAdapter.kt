package com.example.musicbox.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicbox.R
import com.example.musicbox.models.Song

class SongAdapter(
    private val onSongClicked: (Song) -> Unit
) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    private val allSongs = mutableListOf<Song>()
    private val visibleSongs = mutableListOf<Song>()

    fun setSongs(songs: List<Song>) {
        allSongs.clear()
        allSongs.addAll(songs)

        visibleSongs.clear()
        visibleSongs.addAll(songs)
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        visibleSongs.clear()
        if (query.isBlank()) {
            visibleSongs.addAll(allSongs)
        } else {
            val normalizedQuery = query.trim()
            visibleSongs.addAll(
                allSongs.filter {
                    it.title.contains(normalizedQuery, ignoreCase = true) ||
                        it.artist.contains(normalizedQuery, ignoreCase = true)
                }
            )
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song_card, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.bind(visibleSongs[position], onSongClicked)
    }

    override fun getItemCount(): Int = visibleSongs.size

    class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val coverView: View = itemView.findViewById(R.id.viewSongCover)
        private val titleText: TextView = itemView.findViewById(R.id.tvSongTitle)
        private val artistText: TextView = itemView.findViewById(R.id.tvSongArtist)

        fun bind(song: Song, onSongClicked: (Song) -> Unit) {
            coverView.backgroundTintList = ColorStateList.valueOf(song.cardColor)
            titleText.text = song.title
            artistText.text = song.artist
            itemView.setOnClickListener { onSongClicked(song) }
        }
    }
}

