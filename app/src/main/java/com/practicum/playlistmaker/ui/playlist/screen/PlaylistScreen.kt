package com.practicum.playlistmaker.ui.playlist.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.ui.common.CircularProgressIndicator
import com.practicum.playlistmaker.ui.common.CustomTopBar
import com.practicum.playlistmaker.ui.common.TrackListItem
import com.practicum.playlistmaker.ui.playlist.viewModel.PlaylistViewModel
import com.practicum.playlistmaker.ui.theme.BackgroundPrimary
import com.practicum.playlistmaker.ui.theme.IconPrimary

@Composable
fun PlaylistScreen(
    playlistViewModel: PlaylistViewModel,
    onBackClick: () -> Unit,
    onTrackClick: (Track) -> Unit
) {
    val playlistState = playlistViewModel.playlist.collectAsState()
    val playlist = playlistState.value

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
                    top = innerPadding.calculateTopPadding() + 8.dp,
                    bottom = 24.dp
                )
        ) {
            playlist?.let { pl ->
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = 16.dp,
                            start = 24.dp,
                            end = 24.dp
                        ),
                    painter = painterResource(id = R.drawable.ic_playlist),
                    contentDescription = "Обложка плейлиста",
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
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = pl.description,
                            style = MaterialTheme.typography.displaySmall,
                        )
                        val totalMinutes = pl.tracks.sumOf { track ->
                            val parts = track.trackTime.split(":")
                            if (parts.size == 2) {
                                val minutes = parts[0].toIntOrNull() ?: 0
                                val seconds = parts[1].toIntOrNull() ?: 0
                                minutes * 60 + seconds
                            } else 0
                        } / 60
                        val tracksCount = pl.tracks.size
                        val durationText = "$totalMinutes " +
                                totalMinutes.pluralize("минута", "минуты", "минут") +
                                " • $tracksCount ${tracksCount.pluralize("трек", "трека", "треков")}"
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = durationText,
                            style = MaterialTheme.typography.displaySmall,
                        )
                    }
                    IconButton(
                        onClick = {  }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.MoreVert,
                            contentDescription = stringResource(R.string.playlist_more_desc),
                            tint = IconPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(pl.tracks.size) { index ->
                        TrackListItem(
                            track = pl.tracks[index],
                            onClick = { onTrackClick(pl.tracks[index]) }
                        )
                    }
                }
            } ?: run {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
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

