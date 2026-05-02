# Logic Map - MusicBox

This document maps assignment requirements to implementation classes, methods, and behavior in the project.

| Requirement ID | Class / File Name | Function / Method | Implementation Description | Assignment Requirement ID |
|---|---|---|---|---|
| G1 | `app/src/main/java/com/example/musicbox/activities/SplashActivity.kt` | `onCreate` | Creates `Intent` to `MainActivity` and passes user data via `putExtra(MainActivity.EXTRA_USER_NAME, ...)`. | Data Passing Rules |
| G1 | `app/src/main/java/com/example/musicbox/activities/MainActivity.kt` | `openHomeFragment` | Receives Intent extra using `intent.getStringExtra(...)` and forwards value into fragment via `HomeFragment.newInstance(...)`. | Data Passing Rules |
| G1 | `app/src/main/java/com/example/musicbox/fragments/SongDetailFragment.kt` | `newInstance`, `readSongArg` | Transfers `Song` object through `Bundle.putParcelable(...)` and receives via `getParcelable(...)`. | Data Passing Rules |
| G2 | `app/src/main/java/com/example/musicbox/activities/MainActivity.kt` | `openHomeFragment`, `openSearchFragment`, `openLibraryFragment`, `openSettingsFragment` | `MainActivity` acts as navigation coordinator and container by replacing fragments in `fragmentContainer`. | Modular UI Design |
| G2 | `app/src/main/res/layout/activity_main.xml` | Layout structure | Hosts `fragmentContainer` and bottom navigation only; primary content rendered by fragments. | Modular UI Design |
| G3 | `app/src/main/java/com/example/musicbox/adapters/SongAdapter.kt` | `onCreateViewHolder`, `onBindViewHolder`, `SongViewHolder.bind` | Implements custom RecyclerView adapter + ViewHolder for dynamic song item rendering and click callbacks. | Dynamic Data Presentation |
| G3 | `app/src/main/java/com/example/musicbox/adapters/PlaylistAdapter.kt` | `onCreateViewHolder`, `onBindViewHolder`, `PlaylistViewHolder.bind` | Implements custom RecyclerView adapter + ViewHolder for dynamic playlist rows. | Dynamic Data Presentation |
| G4 | `app/src/main/java/com/example/musicbox/activities/*` | N/A | Activity responsibilities isolated in `activities` package. | Clean Architecture Practices |
| G4 | `app/src/main/java/com/example/musicbox/fragments/*` | N/A | Screen UI/content logic isolated in `fragments` package. | Clean Architecture Practices |
| G4 | `app/src/main/java/com/example/musicbox/adapters/*` | N/A | Recycler-specific rendering logic isolated in `adapters` package. | Clean Architecture Practices |
| G4 | `app/src/main/java/com/example/musicbox/models/*` | N/A | Data models (`Song`, `Playlist`) and repository are isolated in `models` package. | Clean Architecture Practices |
| F1 | `app/src/main/AndroidManifest.xml` | Activity declarations + launcher intent filter | `SplashActivity` is launcher; navigation then moves to dashboard (`MainActivity`) with extras. | F1 Intent |
| F1 | `app/src/main/java/com/example/musicbox/activities/SplashActivity.kt` | `onCreate` | Implements splash-to-dashboard transition with Intent Extras. | F1 Intent |
| F2 | `app/src/main/java/com/example/musicbox/models/Song.kt` | Parcelable implementation | `Song` is custom object serialized for Bundle transfer. | F2 Bundle |
| F2 | `app/src/main/java/com/example/musicbox/fragments/SongDetailFragment.kt` | `newInstance`, `readSongArg` | Receives selected RecyclerView item data in detail fragment via Bundle. | F2 Bundle |
| F2 | `app/src/main/java/com/example/musicbox/fragments/HomeFragment.kt` | `onViewCreated` (adapter click callback) | Recycler item click triggers navigation to detail with selected `Song`. | F2 Bundle |
| F3 | `app/src/main/java/com/example/musicbox/fragments/PlaylistFragment.kt` | `onViewCreated` | Configures vertical RecyclerView (`rvPlaylists`) using `LinearLayoutManager(requireContext())`. | F3 RecyclerView |
| F3 | `app/src/main/java/com/example/musicbox/adapters/PlaylistAdapter.kt` | `PlaylistViewHolder` | Custom ViewHolder used with custom row layout (`item_playlist_row.xml`). | F3 RecyclerView |
| F4 | `app/src/main/java/com/example/musicbox/activities/MainActivity.kt` | Fragment open methods + `openSongDetail` | Performs fragment transactions for tab switching and detail navigation with back stack. | F4 Fragment Transactions |
| F5 | `app/src/main/java/com/example/musicbox/fragments/SearchFragment.kt` | `renderSearch`, `afterTextChanged` | Search query toggles browse/results and filters songs + playlists dynamically. | F5 Search / Filter |
| F5 | `app/src/main/java/com/example/musicbox/fragments/PlaylistFragment.kt` | `afterTextChanged` | Library search filters playlist list and updates songs shown. | F5 Search / Filter |
| F5 | `app/src/main/java/com/example/musicbox/models/MockMusicRepository.kt` | `searchSongs`, `searchPlaylists` | Centralized query matching logic for songs/playlists. | F5 Search / Filter |

## Notes / Compliance Caveat
- The app correctly uses Intent Extras and Bundles for interscreen communication.


