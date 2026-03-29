package com.practicum.playlistmaker.ui.trackDetails.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.domain.model.Playlist
import com.practicum.playlistmaker.ui.playlist.components.PlaylistListItem
import com.practicum.playlistmaker.ui.playlists.viewModel.PlaylistsViewModel
import com.practicum.playlistmaker.ui.theme.BackgroundPrimary
import com.practicum.playlistmaker.ui.theme.TextPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistBottomSheet(
    playlists: List<Playlist>,
    isShowPanel: Boolean,
    playlistsViewModel: PlaylistsViewModel,
    onDismissRequest: () -> Unit,
    onPlaylistClick: (Playlist) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    if (isShowPanel) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState,
            containerColor = BackgroundPrimary,
            contentColor = TextPrimary
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.add_track_to_playlist),
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (playlists.isEmpty()) {
                    Image(
                        painter = painterResource(R.drawable.nothing_found),
                        contentDescription = null,
                        modifier = Modifier.size(120.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        stringResource(R.string.playlists_not_found),
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextPrimary
                    )
                } else {
                    LazyColumn(modifier = Modifier
                        .fillMaxSize()
                        .height(300.dp)) {
                        items(playlists.size) { index ->
                            val trackCount by playlistsViewModel.getTracksCountForPlaylist(playlists[index].id).collectAsState(0)
                            PlaylistListItem(
                                playlist = playlists[index],
                                tracksCount = trackCount
                            ) {
                                onPlaylistClick(playlists[index])
                            }
                        }
                    }
                }
            }
        }
    }
}