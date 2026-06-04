package com.practicum.playlistmaker.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.practicum.playlistmaker.ui.navigation.PlaylistHost
import com.practicum.playlistmaker.ui.settings.viewModel.SettingsViewModel
import com.practicum.playlistmaker.ui.theme.PlaylistMakerTheme
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.isSystemInDarkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val settingsViewModel: SettingsViewModel = koinViewModel()

            val savedTheme by settingsViewModel
                .isDarkTheme
                .collectAsState(initial = null)

            val darkTheme = savedTheme
                ?: isSystemInDarkTheme()

            PlaylistMakerTheme(darkTheme = darkTheme) {
                val navController = rememberNavController()
                PlaylistHost(navController = navController)
            }
        }
    }
}