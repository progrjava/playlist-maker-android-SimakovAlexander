package com.practicum.playlistmaker.ui.playlist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.practicum.playlistmaker.ui.playlist.components.AddFloatingButton
import com.practicum.playlistmaker.ui.playlist.components.PlaylistBottomSheet
import com.practicum.playlistmaker.ui.theme.BackgroundPrimary

@Composable
fun PlaylistsScreen() {
    var showBottomSheet by remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = BackgroundPrimary
    ) { innerPadding ->
        AddFloatingButton(modifier = Modifier.padding(innerPadding)) {
            showBottomSheet = true
        }
        PlaylistBottomSheet(
            modifier = Modifier.padding(innerPadding),
            isShowPanel = showBottomSheet,
            onDismissRequest = { showBottomSheet = false },
            content = "Это панель BottomSheet"
        )
    }
}