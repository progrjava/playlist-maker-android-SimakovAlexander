package com.practicum.playlistmaker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.res.stringResource
import com.practicum.playlistmaker.ui.components.common.CustomTopBar
import com.practicum.playlistmaker.ui.theme.PlaylistMakerTheme

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaylistMakerTheme {
                CustomTopBar(
                    showBackButton = true,
                    onBackClick = { finish() },
                    text = stringResource(R.string.settings_title)
                )
            }
        }
    }
}

