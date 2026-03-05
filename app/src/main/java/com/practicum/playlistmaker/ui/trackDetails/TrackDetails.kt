package com.practicum.playlistmaker.ui.trackDetails


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LibraryAdd
import androidx.compose.material.icons.filled.LibraryAddCheck
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.ui.common.CustomTopBar
import com.practicum.playlistmaker.ui.playlist.viewModel.PlaylistsViewModel
import com.practicum.playlistmaker.ui.theme.BackgroundPrimary
import com.practicum.playlistmaker.ui.theme.IconSecondary
import com.practicum.playlistmaker.ui.theme.TextHint
import com.practicum.playlistmaker.ui.theme.TextPrimary
import com.practicum.playlistmaker.ui.trackDetails.components.PlaylistBottomSheet
import kotlinx.coroutines.launch

@Composable
fun TrackDetails(
    track: Track,
    playlistsViewModel: PlaylistsViewModel,
    onBackClick: () -> Unit
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val playlists by playlistsViewModel.playlists.collectAsState(emptyList())
    var currentTrack by remember { mutableStateOf(track) }

    Scaffold(
        topBar = {
            CustomTopBar(
                text = "",
                onBackClick = onBackClick,
                showBackButton = true
            )
        },
        containerColor = BackgroundPrimary
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding() + 26.dp,
                    bottom = 32.dp
                )
                .padding(horizontal = 16.dp)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                painter = painterResource(id = R.drawable.ic_playlist),
                contentDescription = "Обложка трека ${currentTrack.trackName}",
            )
            Text(
                text = currentTrack.trackName,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .padding(top = 24.dp, bottom = 12.dp)
            )
            Text(
                text = currentTrack.artistName,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .padding(top = 54.dp, bottom = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = { showBottomSheet = true },
                    modifier = Modifier
                        .size(56.dp)
                        .background(IconSecondary, CircleShape)
                ) {
                    Icon(
                        imageVector =
                            if (currentTrack.playlistId == 0L) Icons.Filled.LibraryAdd
                            else Icons.Filled.LibraryAddCheck,
                        contentDescription = stringResource(R.string.add_track_to_playlist),
                        tint = Color.White,
                        modifier = Modifier.size(33.dp)
                    )
                }
                IconButton(
                    onClick = {
                        scope.launch {
                            playlistsViewModel.toggleFavorite(currentTrack, !currentTrack.favorite)
                            currentTrack = currentTrack.copy(favorite = !currentTrack.favorite)
                        }
                    },
                    modifier = Modifier
                        .size(56.dp)
                        .background(IconSecondary, CircleShape)
                ) {
                    Icon(
                        imageVector =
                            if (!currentTrack.favorite) Icons.Outlined.FavoriteBorder
                            else Icons.Filled.Favorite,
                        contentDescription = stringResource(R.string.add_track_to_favorites),
                        tint = Color.White,
                        modifier = Modifier.size(33.dp)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(0.dp)
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.track_duration),
                    color = TextHint,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = currentTrack.trackTime,
                    color = TextPrimary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

    PlaylistBottomSheet(
        playlists = playlists,
        isShowPanel = showBottomSheet,
        onDismissRequest = { showBottomSheet = false },
        onPlaylistClick = { playlist ->
            scope.launch {
                playlistsViewModel.insertTrackToPlaylist(currentTrack, playlist.id)
                currentTrack = currentTrack.copy(playlistId = playlist.id)
            }
            showBottomSheet = false
        }
    )
}