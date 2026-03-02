package com.practicum.playlistmaker.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.practicum.playlistmaker.ui.navigation.PlaylistHost
import com.practicum.playlistmaker.ui.search.viewModel.SearchViewModel
import com.practicum.playlistmaker.ui.theme.PlaylistMakerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaylistMakerTheme {
                val navController = rememberNavController()
                PlaylistHost(navController = navController)
            }
        }
    }
}