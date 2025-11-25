package com.practicum.playlistmaker.ui.settings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.practicum.playlistmaker.ui.theme.PlaylistMakerTheme

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaylistMakerTheme {
                SettingsScreen()
            }
        }
    }
}