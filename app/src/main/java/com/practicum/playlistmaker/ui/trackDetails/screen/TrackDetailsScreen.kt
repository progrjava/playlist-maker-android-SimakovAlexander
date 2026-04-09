package com.practicum.playlistmaker.ui.trackDetails.screen


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LibraryAdd
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.ui.common.CustomTopBar
import com.practicum.playlistmaker.ui.playlists.viewModel.PlaylistsViewModel
import com.practicum.playlistmaker.ui.trackDetails.components.PlaylistBottomSheet
import kotlinx.coroutines.launch

@Composable
fun TrackDetailsScreen(
    trackId: Long,
    playlistsViewModel: PlaylistsViewModel,
    onBackClick: () -> Unit
) {
    val track by playlistsViewModel.getTrackById(trackId).collectAsState(initial = null)
    var showBottomSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val playlists by playlistsViewModel.playlists.collectAsState(emptyList())
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
                    top = innerPadding.calculateTopPadding() + 26.dp,
                    bottom = 32.dp
                )
                .padding(horizontal = 16.dp)
        ) {
            track?.let { currentTrack ->
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    model = currentTrack.image,
                    placeholder = painterResource(id = R.drawable.ic_playlist),
                    error = painterResource(id = R.drawable.ic_playlist),
                    contentDescription = "Обложка трека ${currentTrack.trackName}",
                )
                Text(
                    text = currentTrack.trackName,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .padding(top = 24.dp, bottom = 12.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = currentTrack.artistName,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    color = MaterialTheme.colorScheme.onBackground
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
                            .background(MaterialTheme.colorScheme.outline, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.LibraryAdd,
                            contentDescription = stringResource(R.string.add_track_to_playlist),
                            tint = Color.White,
                            modifier = Modifier.size(33.dp)
                        )
                    }
                    IconButton(
                        onClick = {
                            scope.launch {
                                playlistsViewModel.toggleFavorite(currentTrack, !currentTrack.favorite)
                            }
                        },
                        modifier = Modifier
                            .size(56.dp)
                            .background(MaterialTheme.colorScheme.outline, CircleShape)
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
                        color = MaterialTheme.colorScheme.outline,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = currentTrack.trackTime,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }


    val message = stringResource(
        R.string.added_to_playlist
    )
    track?.let { currentTrack ->
        PlaylistBottomSheet(
            playlists = playlists,
            isShowPanel = showBottomSheet,
            playlistsViewModel = playlistsViewModel,
            onDismissRequest = { showBottomSheet = false },
            onPlaylistClick = { playlist ->
                scope.launch {
                    playlistsViewModel.insertTrackToPlaylist(currentTrack, playlist.id)
                    Toast.makeText(
                        context,
                        message + playlist.name,
                        Toast.LENGTH_SHORT
                    ).show()
                    showBottomSheet = false
                }
            }
        )
    }

}