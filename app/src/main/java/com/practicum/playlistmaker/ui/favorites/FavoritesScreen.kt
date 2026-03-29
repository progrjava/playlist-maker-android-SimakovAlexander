package com.practicum.playlistmaker.ui.favorites


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.ui.common.CustomTopBar
import com.practicum.playlistmaker.ui.common.DeleteAlertDialog
import com.practicum.playlistmaker.ui.common.TrackListItem
import com.practicum.playlistmaker.ui.playlists.viewModel.PlaylistsViewModel
import com.practicum.playlistmaker.ui.search.components.CenteredColumn
import com.practicum.playlistmaker.ui.theme.BackgroundPrimary
import com.practicum.playlistmaker.ui.theme.TextPrimary
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    playlistsViewModel: PlaylistsViewModel,
    onTrackClick: (Track) -> Unit,
    onBackClick: () -> Unit
) {
    val favorites = playlistsViewModel.favoriteList.collectAsState(emptyList())
    var trackToDelete by remember { mutableStateOf<Track?>(null) }

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CustomTopBar(
                text = stringResource(R.string.favorites_screen_title),
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
            if (favorites.value.isEmpty()) {
                CenteredColumn {
                    Image(
                        painter = painterResource(R.drawable.nothing_found),
                        contentDescription = null,
                        modifier = Modifier.size(120.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        stringResource(R.string.favorites_not_found),
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary
                    )
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(favorites.value.size) { index ->
                        val track = favorites.value[index]
                        TrackListItem(
                            track = track,
                            onClick = { onTrackClick(track) },
                            onLongClick = {
                                trackToDelete = track
                            }
                        )
                    }
                }
            }
        }

        trackToDelete?.let { track ->
            DeleteAlertDialog(
                onConfirm = {
                    scope.launch {
                        playlistsViewModel.toggleFavorite(track, false)
                        trackToDelete = null
                    }
                },
                onDismiss = { trackToDelete = null },
                objectToDelete = track
            )
        }
    }
}