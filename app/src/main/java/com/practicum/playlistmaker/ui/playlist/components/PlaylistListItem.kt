package com.practicum.playlistmaker.ui.playlist.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.domain.model.Playlist
import com.practicum.playlistmaker.ui.theme.TextHint
import com.practicum.playlistmaker.ui.theme.TextPrimary

@Composable
fun PlaylistListItem(
    playlist: Playlist,
    tracksCount: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onClick.invoke() })
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Image(
            modifier = Modifier.size(45.dp),
            painter = painterResource(id = R.drawable.ic_music),
            contentDescription = playlist.name,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                playlist.name,
                style = MaterialTheme.typography.bodyLarge,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            val text = "$tracksCount " + tracksCount.pluralize("трек", "трека", "треков")
            Text(
                text,
                style = MaterialTheme.typography.bodySmall,
                color = TextHint
            )
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
