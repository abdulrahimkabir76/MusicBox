package com.example.musicbox.models

import android.graphics.Color

object MockMusicRepository {
    private val chipSongs: Map<String, List<Song>> = mapOf(
        "music" to listOf(
            Song("song_neon_nights", "Neon Nights", "Ava Grey", Color.parseColor("#B06060")),
            Song("song_afterglow", "Afterglow Drive", "Kai Rivers", Color.parseColor("#1DB954")),
            Song("song_city_lights", "City Lights", "Nova Lane", Color.parseColor("#508090"))
        ),
        "podcasts" to listOf(
            Song("podcast_midnight_memo", "Midnight Memo", "The Audio Room", Color.parseColor("#7449A8")),
            Song("podcast_brain_fuel", "Brain Fuel", "Curious Lab", Color.parseColor("#4878A8")),
            Song("podcast_daily_bit", "The Daily Bit", "K. Harper", Color.parseColor("#6E7D8C"))
        ),
        "live" to listOf(
            Song("live_electric_set", "Electric Set Live", "Luma", Color.parseColor("#D14E8F")),
            Song("live_rooftop_jam", "Rooftop Jam", "Skyline Duo", Color.parseColor("#9F5A47")),
            Song("live_city_sessions", "City Sessions", "NXT Stage", Color.parseColor("#3C7E77"))
        ),
        "wrapped" to listOf(
            Song("wrapped_top_hit", "Your Top Hit", "MusicBox Wrapped", Color.parseColor("#8A65C2")),
            Song("wrapped_throwback", "Throwback Anthem", "MusicBox Wrapped", Color.parseColor("#B777D1")),
            Song("wrapped_late_night", "Late Night Replay", "MusicBox Wrapped", Color.parseColor("#5D86D6"))
        ),
        "k-pop" to listOf(
            Song("kpop_star_dust", "Star Dust", "Lunar Bloom", Color.parseColor("#F05EA7")),
            Song("kpop_echo", "Echo", "NOVA7", Color.parseColor("#C66AF1")),
            Song("kpop_hype_line", "Hype Line", "PRISM", Color.parseColor("#5A8BE0"))
        ),
        "fitness" to listOf(
            Song("fit_power_run", "Power Run", "Pulse Theory", Color.parseColor("#2E9D63")),
            Song("fit_core_mode", "Core Mode", "Drive Unit", Color.parseColor("#4E8F56")),
            Song("fit_peak", "Peak Hour", "Tempo Lab", Color.parseColor("#7AAE44"))
        ),
        "focus" to listOf(
            Song("focus_deep_work", "Deep Work", "Loft Signals", Color.parseColor("#4D6D96")),
            Song("focus_soft_rain", "Soft Rain", "Quiet Tone", Color.parseColor("#5D7085")),
            Song("focus_drift", "Drift", "Mono Light", Color.parseColor("#7A6F8D"))
        )
    )

    private val libraryPlaylistsData: List<Playlist> = listOf(
        Playlist(
            id = "playlist_commute",
            name = "Daily Commute",
            songs = listOf(
                Song("c1", "Fast Lane", "Northbound", Color.parseColor("#5079A2")),
                Song("c2", "City Pulse", "Mika J", Color.parseColor("#8D5A74")),
                Song("c3", "Signal Green", "Metroline", Color.parseColor("#2F8A5E"))
            )
        ),
        Playlist(
            id = "playlist_focus",
            name = "Focus Flow",
            songs = listOf(
                Song("f1", "Quiet Loop", "Glass Echo", Color.parseColor("#6A7F96")),
                Song("f2", "Paper Planes", "Soma", Color.parseColor("#78839A")),
                Song("f3", "Nimbus", "Elin Row", Color.parseColor("#627089"))
            )
        ),
        Playlist(
            id = "playlist_gym",
            name = "Gym Grind",
            songs = listOf(
                Song("g1", "Rush", "Volt", Color.parseColor("#638F40")),
                Song("g2", "Set Repeat", "Rift", Color.parseColor("#809C42")),
                Song("g3", "Final Rep", "Axis", Color.parseColor("#4C8152"))
            )
        )
    )

    fun recentlyPlayedSongs(): List<Song> = songsForChip("music")

    fun songsForChip(chipName: String): List<Song> {
        val key = chipName.trim().lowercase()
        return chipSongs[key] ?: chipSongs.getValue("music")
    }

    fun libraryPlaylists(): List<Playlist> = libraryPlaylistsData

    fun searchPlaylists(query: String): List<Playlist> {
        if (query.isBlank()) return libraryPlaylistsData
        val normalized = query.trim()
        return libraryPlaylistsData.filter { playlist ->
            playlist.name.contains(normalized, ignoreCase = true) ||
                playlist.songs.any { song ->
                    song.title.contains(normalized, ignoreCase = true) ||
                        song.artist.contains(normalized, ignoreCase = true)
                }
        }
    }

    fun allSongs(): List<Song> {
        val fromChips = chipSongs.values.flatten()
        val fromPlaylists = libraryPlaylistsData.flatMap { it.songs }
        return (fromChips + fromPlaylists).distinctBy { it.id }
    }

    fun searchSongs(query: String): List<Song> {
        if (query.isBlank()) return emptyList()
        val normalized = query.trim()
        return allSongs().filter { song ->
            song.title.contains(normalized, ignoreCase = true) ||
                song.artist.contains(normalized, ignoreCase = true)
        }
    }
}

