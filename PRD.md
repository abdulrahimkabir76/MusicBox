# MusicBox: Spotify Clone - Backend Implementation PRD

## Overview
This document outlines the phased development plan for transitioning the MusicBox UI from static XML layouts to a fully functional, data-driven Android application. It adheres to **SOLID**, **DRY**, and **KISS** principles while strictly following the constraints of **Assignment #03**.

## Strategic Context
The implementation will utilize the existing UI designs (e.g., `layout_recently_played.xml`, `activity_main.xml`, `layout_tag_cloud.xml`, etc.) as the blueprint for dynamic data presentation. Static components, specifically the repeating items in `layout_recently_played.xml`, will be refactored into modular `RecyclerView` patterns.

---

## Global Constraints (Strict Requirements)
1. **Data Passing:** Use `Intent` Extras and `Bundles` only. No static globals, shared preferences, or singletons for inter-screen communication.
2. **Modular UI:** Primary content must be implemented using `Fragments`. `Activities` should act only as containers or navigation coordinators.
3. **Dynamic Lists:** All lists (e.g., Recently Played, Playlists) must be implemented using `RecyclerView` with a custom `Adapter` and `ViewHolder`.
4. **Clean Architecture:** Organize the project into folders: `activities`, `fragments`, `adapters`, and `models`.

---

## Implementation Phases

### Phase 1: Models & Parcelable Implementation (KISS/DRY)
**Goal:** Define data structures that map to the existing UI fields (Title, Artist, Cover Image/Color).
- **Task:** Create `Song` and `Playlist` data classes in `com.example.musicbox.models`.
- **Details:** Must implement `Parcelable` (using `@Parcelize`) to allow passing objects via Bundles/Intents as per F2.
- **Test Case:** Create a `Song` object and verify it can be added to a `Bundle` and retrieved without loss of data.

### Phase 2: Refactoring Static Layouts to Dynamic (F3)
**Goal:** Convert static `HorizontalScrollView` in `layout_recently_played.xml` into a dynamic `RecyclerView`.
- **Task:** 
    1. Create `item_song_card.xml` by extracting the repeating `LinearLayout` structure from `layout_recently_played.xml`.
    2. Replace the `HorizontalScrollView` and its inner `LinearLayout` in `layout_recently_played.xml` with a `androidx.recyclerview.widget.RecyclerView`.
- **Test Case:** The `RecyclerView` correctly inflates `item_song_card.xml` and scrolls horizontally.

### Phase 3: Adapter Implementation (SOLID)
**Goal:** Create a reusable `SongAdapter` in `com.example.musicbox.adapters`.
- **Task:** Implement `RecyclerView.Adapter` that binds `Song` data to `item_song_card.xml`. Use the `View`'s `backgroundTint` to reflect the colors defined in the original static layout.
- **Test Case:** `SongAdapter` correctly displays a list of 5 mock songs with their respective titles and colors.

### Phase 4: Fragment-Based Navigation & Data Passing (F1/F4)
**Goal:** Set up `MainActivity` as a coordinator and implement the first navigation jump.
- **Task:**
    1. Implement `HomeFragment` to host the `layout_recently_played` logic.
    2. Navigate from a (to-be-created) `SplashActivity` to `MainActivity`, passing user data via `Intent` extras.
    3. Use `supportFragmentManager` for switching between `HomeFragment` and other fragments.
- **Test Case:** Application starts on a splash screen and moves to the dashboard, displaying a "Welcome [User]" message passed via Intent.

### Phase 5: Detail Navigation & Object Transfer (F2)
**Goal:** Transfer a `Song` object from the `RecyclerView` to a `DetailFragment`.
- **Task:** 
    1. Implement a click listener in `SongAdapter`.
    2. When an item is clicked, `MainActivity` should replace the current fragment with `SongDetailFragment`.
    3. Pass the `Song` object using `bundle.putParcelable()`.
- **Test Case:** Clicking "Discover Weekly" in the list opens a detail screen showing "Discover Weekly" and its specific artist information.

### Phase 6: Search & Filtering Logic (F5)
**Goal:** Implement real-time filtering in the `SearchFragment`.
- **Task:** Add search logic to the `SongAdapter` to filter items based on title or artist.
- **Test Case:** Typing "Daily" in the search bar filters the list to show only "Daily Mix 1".
