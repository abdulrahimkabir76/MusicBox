package com.example.musicbox.activities

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.musicbox.R
import com.example.musicbox.fragments.HomeFragment
import com.example.musicbox.fragments.PlaylistFragment
import com.example.musicbox.fragments.SearchFragment
import com.example.musicbox.fragments.SettingsFragment
import com.example.musicbox.fragments.SongDetailFragment
import com.example.musicbox.models.Song

class MainActivity : AppCompatActivity() {

    private enum class Tab {
        HOME,
        SEARCH,
        LIBRARY,
        SETTINGS
    }

    private var selectedTab: Tab = Tab.HOME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.navHome).setOnClickListener { openHomeFragment() }
        findViewById<View>(R.id.navSearch).setOnClickListener { openSearchFragment() }
        findViewById<View>(R.id.navLibrary).setOnClickListener { openLibraryFragment() }
        findViewById<View>(R.id.navSettings).setOnClickListener { openSettingsFragment() }

        if (savedInstanceState == null) {
            openHomeFragment()
        } else {
            updateNavSelection(selectedTab)
        }
    }

    fun openSongDetail(song: Song) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragmentContainer,
                SongDetailFragment.newInstance(song)
            )
            .addToBackStack(SongDetailFragment.TAG)
            .commit()
    }

    private fun openHomeFragment() {
        val userName = intent.getStringExtra(EXTRA_USER_NAME).orEmpty()
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragmentContainer,
                HomeFragment.newInstance(userName)
            )
            .commit()
        updateNavSelection(Tab.HOME)
    }

    private fun openSearchFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, SearchFragment.newInstance())
            .commit()
        updateNavSelection(Tab.SEARCH)
    }

    private fun openLibraryFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, PlaylistFragment.newInstance())
            .commit()
        updateNavSelection(Tab.LIBRARY)
    }

    private fun openSettingsFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, SettingsFragment.newInstance())
            .commit()
        updateNavSelection(Tab.SETTINGS)
    }

    private fun updateNavSelection(tab: Tab) {
        selectedTab = tab

        val selectedColor = ContextCompat.getColor(this, R.color.white)
        val unselectedColor = ContextCompat.getColor(this, R.color.spotify_light_gray)

        setNavItemState(R.id.ivNavHome, R.id.tvNavHome, tab == Tab.HOME, selectedColor, unselectedColor)
        setNavItemState(R.id.ivNavSearch, R.id.tvNavSearch, tab == Tab.SEARCH, selectedColor, unselectedColor)
        setNavItemState(R.id.ivNavLibrary, R.id.tvNavLibrary, tab == Tab.LIBRARY, selectedColor, unselectedColor)
        setNavItemState(R.id.ivNavSettings, R.id.tvNavSettings, tab == Tab.SETTINGS, selectedColor, unselectedColor)
    }

    private fun setNavItemState(
        iconId: Int,
        labelId: Int,
        selected: Boolean,
        selectedColor: Int,
        unselectedColor: Int
    ) {
        val color = if (selected) selectedColor else unselectedColor
        findViewById<ImageView>(iconId).imageTintList = ColorStateList.valueOf(color)
        findViewById<TextView>(labelId).setTextColor(color)
    }

    companion object {
        const val EXTRA_USER_NAME = "extra_user_name"
    }
}

