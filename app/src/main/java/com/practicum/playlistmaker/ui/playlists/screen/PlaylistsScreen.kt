package com.practicum.playlistmaker.ui.playlists.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.ui.common.CustomTopBar
import com.practicum.playlistmaker.ui.playlists.components.AddFloatingButton
import com.practicum.playlistmaker.ui.playlist.components.PlaylistListItem
import com.practicum.playlistmaker.ui.playlists.viewModel.PlaylistsViewModel
import com.practicum.playlistmaker.ui.search.components.CenteredColumn
import com.practicum.playlistmaker.ui.theme.BackgroundPrimary
import com.practicum.playlistmaker.ui.theme.TextPrimary

@Composable
fun PlaylistsScreen(
    playlistsViewModel: PlaylistsViewModel,
    addNewPlaylist: () -> Unit,
    navigateToPlaylist: (Long) -> Unit,
    onBackClick: () -> Unit
) {
    val playlists by playlistsViewModel.playlists.collectAsState(emptyList())

    Scaffold(
        topBar = {
            CustomTopBar(
                text = stringResource(R.string.playlists_title),
                onBackClick = onBackClick,
                showBackButton = true
            )
        },
        containerColor = BackgroundPrimary
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .fillMaxSize()
        ) {
            if (playlists.isEmpty()) {
                CenteredColumn {
                    Image(
                        painter = painterResource(R.drawable.nothing_found),
                        contentDescription = null,
                        modifier = Modifier.size(120.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        stringResource(R.string.playlists_not_found),
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary
                    )
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(playlists.size) { index ->
                        val tracksCount by playlistsViewModel.getTracksCountForPlaylist(playlists[index].id).collectAsState(0)
                        PlaylistListItem(
                            playlist = playlists[index],
                            tracksCount = tracksCount
                        ) {
                            navigateToPlaylist(playlists[index].id)
                        }
                    }
                }
            }
            AddFloatingButton {
                addNewPlaylist()
            }
        }
    }
}