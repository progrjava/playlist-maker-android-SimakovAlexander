package com.practicum.playlistmaker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.practicum.playlistmaker.ui.components.CustomTopBar
import com.practicum.playlistmaker.ui.theme.PlaylistMakerTheme

class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaylistMakerTheme {
                CustomTopBar(
                    showBackButton = true,
                    onBackClick = { finish() },
                    text = "Поиск",
                )
            }
        }
    }
}


