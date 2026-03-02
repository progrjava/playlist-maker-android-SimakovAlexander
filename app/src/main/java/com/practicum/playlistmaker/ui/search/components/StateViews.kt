package com.practicum.playlistmaker.ui.search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.TravelExplore
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.ui.common.CircularProgressIndicator
import com.practicum.playlistmaker.ui.common.TrackListItem
import com.practicum.playlistmaker.ui.theme.BluePrimary
import com.practicum.playlistmaker.ui.theme.TextPrimary

@Composable
fun InitialStateView() {
    CenteredColumn {
        Icon(
            imageVector = Icons.Outlined.TravelExplore,
            contentDescription = null,
            modifier = Modifier.size(120.dp),
            tint = BluePrimary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.search_initial),
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary
        )
    }
}

@Composable
fun SearchingView() {
    CenteredColumn {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            stringResource(R.string.search_searching),
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary
        )
    }
}

@Composable
fun NothingFoundView() {
    CenteredColumn {
        Image(
            painter = painterResource(R.drawable.nothing_found),
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            stringResource(R.string.search_nothing_found),
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary
        )
    }
}

@Composable
fun FoundTracksView(tracks: List<Track>, onClick: (Int?) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(tracks.size) { index ->
            TrackListItem(
                track = tracks[index],
                onClick = { onClick(index) }
            )
        }
    }
}

@Composable
fun FailView(error: String) {
    CenteredColumn {
        Image(
            painter = painterResource(R.drawable.searching_error),
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            stringResource(R.string.search_error),
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            error,
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary
        )
    }
}

@Composable
fun CenteredColumn(content: @Composable ColumnScope.() -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            content = content
        )
    }
}