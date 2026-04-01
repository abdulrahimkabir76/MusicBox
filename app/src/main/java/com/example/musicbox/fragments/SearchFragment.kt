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

class SearchFragment : Fragment(R.layout.fragment_search) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (childFragmentManager.findFragmentByTag(BROWSE_TAG) == null) {
            childFragmentManager.beginTransaction()
                .replace(R.id.browseContainer, BrowseFragment.newInstance(), BROWSE_TAG)
                .commitNow()
        }

        val songAdapter = SongAdapter { song ->
            (activity as? MainActivity)?.openSongDetail(song)
        }

        view.findViewById<RecyclerView>(R.id.rvSongResults).apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = songAdapter
        }

        val playlistAdapter = PlaylistAdapter { playlist ->
            playlist.songs.firstOrNull()?.let { song ->
                (activity as? MainActivity)?.openSongDetail(song)
            }
        }

        view.findViewById<RecyclerView>(R.id.rvPlaylistResults).apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = playlistAdapter
        }

        val browseContainer = view.findViewById<View>(R.id.browseContainer)
        val resultsContainer = view.findViewById<View>(R.id.resultsContainer)
        val emptyState = view.findViewById<TextView>(R.id.tvNoResults)

        fun renderSearch(query: String) {
            if (query.isBlank()) {
                browseContainer.visibility = View.VISIBLE
                resultsContainer.visibility = View.GONE
                return
            }

            browseContainer.visibility = View.GONE
            resultsContainer.visibility = View.VISIBLE

            val songs = MockMusicRepository.searchSongs(query)
            val playlists = MockMusicRepository.searchPlaylists(query)

            songAdapter.setSongs(songs)
            playlistAdapter.setPlaylists(playlists)
            emptyState.visibility = if (songs.isEmpty() && playlists.isEmpty()) View.VISIBLE else View.GONE
        }

        renderSearch("")

        view.findViewById<EditText>(R.id.etSearch).addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

            override fun afterTextChanged(s: Editable?) {
                renderSearch(s?.toString().orEmpty())
            }
        })
    }

    companion object {
        private const val BROWSE_TAG = "SearchBrowseFragment"

        fun newInstance(): SearchFragment = SearchFragment()
    }
}

