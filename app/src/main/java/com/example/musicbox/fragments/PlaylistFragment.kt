package com.example.musicbox.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicbox.R
import com.example.musicbox.activities.MainActivity
import com.example.musicbox.adapters.PlaylistAdapter
import com.example.musicbox.adapters.SongAdapter
import com.example.musicbox.models.MockMusicRepository
import com.example.musicbox.models.Playlist

class PlaylistFragment : Fragment(R.layout.fragment_library) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val songsHeader = view.findViewById<TextView>(R.id.tvPlaylistSongsHeader)

        val songAdapter = SongAdapter { song ->
            (activity as? MainActivity)?.openSongDetail(song)
        }
        view.findViewById<RecyclerView>(R.id.rvPlaylistSongs).apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = songAdapter
        }

        val playlistAdapter = PlaylistAdapter { playlist ->
            showPlaylistSongs(playlist, songsHeader, songAdapter)
        }
        view.findViewById<RecyclerView>(R.id.rvPlaylists).apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = playlistAdapter
        }

        playlistAdapter.setPlaylists(MockMusicRepository.libraryPlaylists())
        playlistAdapter.firstVisiblePlaylist()?.let {
            showPlaylistSongs(it, songsHeader, songAdapter)
        }

        view.findViewById<EditText>(R.id.etLibrarySearch)
            .addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

                override fun afterTextChanged(s: Editable?) {
                    playlistAdapter.setPlaylists(
                        MockMusicRepository.searchPlaylists(s?.toString().orEmpty())
                    )
                    val firstMatch = playlistAdapter.firstVisiblePlaylist()
                    if (firstMatch == null) {
                        songsHeader.text = getString(R.string.library_no_results)
                        songAdapter.setSongs(emptyList())
                    } else {
                        showPlaylistSongs(firstMatch, songsHeader, songAdapter)
                    }
                }
            })
    }

    private fun showPlaylistSongs(
        playlist: Playlist,
        songsHeader: TextView,
        songAdapter: SongAdapter
    ) {
        songsHeader.text = getString(R.string.library_songs_header, playlist.name)
        songAdapter.setSongs(playlist.songs)
    }

    companion object {
        fun newInstance(): PlaylistFragment = PlaylistFragment()
    }
}

