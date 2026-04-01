package com.example.musicbox.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicbox.R
import com.example.musicbox.activities.MainActivity
import com.example.musicbox.adapters.SongAdapter
import com.example.musicbox.models.MockMusicRepository

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var selectedChipId: Int = R.id.tag_music

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userName = arguments?.getString(ARG_USER_NAME).orEmpty().ifBlank { "Guest" }
        view.findViewById<TextView>(R.id.tvWelcome).text = getString(R.string.welcome_user, userName)

        val adapter = SongAdapter { song ->
            (activity as? MainActivity)?.openSongDetail(song)
        }
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvRecentlyPlayed)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        bindChip(view, R.id.tag_music, "music", adapter)
        bindChip(view, R.id.tag_podcasts, "podcasts", adapter)
        bindChip(view, R.id.tag_live, "live", adapter)
        bindChip(view, R.id.tag_wrapped, "wrapped", adapter)
        bindChip(view, R.id.tag_kpop, "k-pop", adapter)
        bindChip(view, R.id.tag_fitness, "fitness", adapter)
        bindChip(view, R.id.tag_focus, "focus", adapter)

        setSelectedChip(view, selectedChipId)
        adapter.setSongs(MockMusicRepository.recentlyPlayedSongs())
    }

    private fun bindChip(view: View, chipId: Int, chipKey: String, adapter: SongAdapter) {
        view.findViewById<TextView>(chipId).setOnClickListener {
            selectedChipId = chipId
            setSelectedChip(view, selectedChipId)
            adapter.setSongs(MockMusicRepository.songsForChip(chipKey))
        }
    }

    private fun setSelectedChip(view: View, selectedId: Int) {
        val selectedBg = ContextCompat.getColor(requireContext(), R.color.spotify_green)
        val unselectedBg = ContextCompat.getColor(requireContext(), R.color.spotify_dark_gray)
        val selectedText = ContextCompat.getColor(requireContext(), R.color.spotify_black)
        val unselectedText = ContextCompat.getColor(requireContext(), R.color.white)
        val chips = listOf(
            R.id.tag_music,
            R.id.tag_podcasts,
            R.id.tag_live,
            R.id.tag_wrapped,
            R.id.tag_kpop,
            R.id.tag_fitness,
            R.id.tag_focus
        )

        chips.forEach { id ->
            val chip = view.findViewById<TextView>(id)
            val isSelected = id == selectedId
            chip.backgroundTintList = ColorStateList.valueOf(
                if (isSelected) selectedBg else unselectedBg
            )
            chip.setTextColor(if (isSelected) selectedText else unselectedText)
        }
    }

    companion object {
        private const val ARG_USER_NAME = "arg_user_name"

        fun newInstance(userName: String): HomeFragment =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_USER_NAME, userName)
                }
            }
    }
}

