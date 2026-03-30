package com.example.musicbox.fragments

import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.musicbox.R
import com.example.musicbox.models.Song

class SongDetailFragment : Fragment(R.layout.fragment_now_playing) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val song = readSongArg() ?: return
        view.findViewById<View>(R.id.album_art).backgroundTintList = ColorStateList.valueOf(song.cardColor)
        view.findViewById<TextView>(R.id.song_title).text = song.title
        view.findViewById<TextView>(R.id.artist_name).text = song.artist

        view.findViewById<View>(R.id.btn_down).setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun readSongArg(): Song? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(ARG_SONG, Song::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable(ARG_SONG)
        }
    }

    companion object {
        const val TAG = "SongDetailFragment"
        private const val ARG_SONG = "arg_song"

        fun newInstance(song: Song): SongDetailFragment =
            SongDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_SONG, song)
                }
            }
    }
}

