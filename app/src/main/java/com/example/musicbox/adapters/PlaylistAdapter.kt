package com.example.musicbox.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicbox.R
import com.example.musicbox.models.Playlist

class PlaylistAdapter(
    private val onPlaylistClicked: (Playlist) -> Unit
) : RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>() {

    private val allPlaylists = mutableListOf<Playlist>()
    private val visiblePlaylists = mutableListOf<Playlist>()
    private var selectedPlaylistId: String? = null

    fun setPlaylists(playlists: List<Playlist>) {
        allPlaylists.clear()
        allPlaylists.addAll(playlists)

        visiblePlaylists.clear()
        visiblePlaylists.addAll(playlists)

        if (selectedPlaylistId == null || visiblePlaylists.none { it.id == selectedPlaylistId }) {
            selectedPlaylistId = visiblePlaylists.firstOrNull()?.id
        }
        notifyDataSetChanged()
    }


    fun firstVisiblePlaylist(): Playlist? = visiblePlaylists.firstOrNull()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_playlist_row, parent, false)
        return PlaylistViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val playlist = visiblePlaylists[position]
        holder.bind(
            playlist = playlist,
            selected = playlist.id == selectedPlaylistId,
            onClick = {
                selectedPlaylistId = playlist.id
                notifyDataSetChanged()
                onPlaylistClicked(playlist)
            }
        )
    }

    override fun getItemCount(): Int = visiblePlaylists.size

    class PlaylistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameText: TextView = itemView.findViewById(R.id.tvPlaylistName)
        private val metaText: TextView = itemView.findViewById(R.id.tvPlaylistMeta)

        fun bind(playlist: Playlist, selected: Boolean, onClick: () -> Unit) {
            nameText.text = playlist.name
            metaText.text = itemView.context.getString(
                R.string.playlist_meta,
                playlist.songs.size
            )

            itemView.alpha = if (selected) 1f else 0.76f
            itemView.setOnClickListener { onClick() }
        }
    }
}

