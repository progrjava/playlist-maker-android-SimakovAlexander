package com.practicum.playlistmaker.ui.search

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            com.practicum.playlistmaker.ui.theme.PlaylistMakerTheme {
                SearchScreen()
            }
        }
    }
}