package com.practicum.playlistmaker.ui.navigation

enum class Screen(val route: String) {
    MAIN("main"),
    SEARCH("search"),
    PLAYLISTS("playlists"),
    NEW_PLAYLIST("new_playlist"),
    EDIT_PLAYLIST("edit_playlist/{playlistId}"),
    FAVORITES("favorites"),
    SETTINGS("settings"),
    TRACK_DETAILS("track_details/{trackId}"),
    PLAYLIST_DETAILS("playlist_details/{playlistId}")
}