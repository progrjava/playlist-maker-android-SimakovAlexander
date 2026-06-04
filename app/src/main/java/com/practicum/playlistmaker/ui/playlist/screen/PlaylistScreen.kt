package com.practicum.playlistmaker.ui.playlist.screen

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.ui.common.CircularProgressIndicator
import com.practicum.playlistmaker.ui.common.CustomTopBar
import com.practicum.playlistmaker.ui.common.DeleteAlertDialog
import com.practicum.playlistmaker.ui.common.TrackListItem
import com.practicum.playlistmaker.ui.playlist.components.PlaylistListItem
import com.practicum.playlistmaker.ui.playlist.viewModel.PlaylistViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistScreen(
    playlistViewModel: PlaylistViewModel,
    onBackClick: () -> Unit,
    onTrackClick: (Track) -> Unit,
    onEditPlaylist: (Long) -> Unit
) {
    val playlist by playlistViewModel.playlist.collectAsState()
    val tracks by playlistViewModel.tracks.collectAsState()
    var trackToDelete by remember { mutableStateOf<Track?>(null) }
    var showDeletePlaylistDialog by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CustomTopBar(
                text = "",
                onBackClick = onBackClick,
                showBackButton = true
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding() + 8.dp,
                    bottom = 24.dp
                )
        ) {
            playlist?.let { pl ->
                AsyncImage(
                    model = pl.image,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = 16.dp,
                            start = 24.dp,
                            end = 24.dp
                        )
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.ic_playlist),
                    error = painterResource(id = R.drawable.ic_playlist),
                    contentDescription = stringResource(R.string.playlist_cover),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 8.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = pl.name,
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        pl.description?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.displaySmall,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = getDurationText(tracks),
                            style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    IconButton(
                        onClick = { showBottomSheet = true }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.MoreVert,
                            contentDescription = stringResource(R.string.playlist_more_desc),
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(tracks) { track ->
                        TrackListItem(
                            track = track,
                            onClick = { onTrackClick(track) },
                            onLongClick = {
                                trackToDelete = track
                            }
                        )
                    }
                }
            } ?: run {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
        trackToDelete?.let { track ->
            DeleteAlertDialog(
                onConfirm = {
                    playlistViewModel.removeTrack(track)
                    trackToDelete = null
                },
                onDismiss = { trackToDelete = null },
                objectToDelete = track
            )
        }

        if (showDeletePlaylistDialog) {
            playlist?.let { pl ->
                DeleteAlertDialog(
                    onConfirm = {
                        playlistViewModel.deletePlaylist()
                        showDeletePlaylistDialog = false
                        onBackClick()
                    },
                    onDismiss = { showDeletePlaylistDialog = false },
                    objectToDelete = pl
                )
            }
        }
    }
    playlist?.let { pl ->
        if (showBottomSheet) {
            val shareMessage = stringResource(
                R.string.share_playlist_message,
                pl.name,
                tracks.size,
                pl.description ?: stringResource(R.string.no_description),
                tracks.joinToString(", ") { it.trackName }
            )
            val shareVia = stringResource(R.string.share_via)
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = { showBottomSheet = false },
                containerColor = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier.padding(bottom = 32.dp)
                ) {
                    PlaylistListItem(
                        playlist = pl,
                        tracksCount = tracks.size,
                        onClick = { showBottomSheet = false }
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        text = stringResource(R.string.share_playlist),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                scope.launch {
                                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                        putExtra(
                                            Intent.EXTRA_TEXT,
                                            shareMessage
                                        )
                                        type = "text/plain"
                                    }
                                    context.startActivity(Intent.createChooser(shareIntent, shareVia))
                                    sheetState.hide()
                                    showBottomSheet = false
                                }
                            }
                            .padding(horizontal = 16.dp, vertical = 21.dp)
                    )
                    Text(
                        text = stringResource(R.string.edit_playlist_info),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                scope.launch {
                                    sheetState.hide()
                                    showBottomSheet = false
                                    playlist?.id?.let { id -> onEditPlaylist(id) }
                                }
                            }
                            .padding(horizontal = 16.dp, vertical = 21.dp)
                    )
                    Text(
                        text = stringResource(R.string.delete_playlist),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                scope.launch {
                                    sheetState.hide()
                                    showBottomSheet = false
                                    showDeletePlaylistDialog = true
                                }
                            }
                            .padding(horizontal = 16.dp, vertical = 21.dp)
                    )
                }
            }
        }
    }

}


private fun Int.pluralize(one: String, few: String, many: String): String {
    val lastDigit = this % 10
    val lastTwo = this % 100
    return when {
        lastTwo in 11..19 -> many
        lastDigit == 1 -> one
        lastDigit in 2..4 -> few
        else -> many
    }
}

private fun getDurationText(tracks: List<Track>): String {
    val totalMinutes = tracks.sumOf { track ->
        val parts = track.trackTime.split(":")
        if (parts.size == 2) {
            val minutes = parts[0].toIntOrNull() ?: 0
            val seconds = parts[1].toIntOrNull() ?: 0
            minutes * 60 + seconds
        } else 0
    } / 60
    val tracksCount = tracks.size
    val durationText = "$totalMinutes " +
            totalMinutes.pluralize("минута", "минуты", "минут") +
            " • $tracksCount ${tracksCount.pluralize("трек", "трека", "треков")}"
    return durationText
}

