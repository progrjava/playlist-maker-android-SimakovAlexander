package com.practicum.playlistmaker.ui.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.domain.model.Playlist
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.ui.theme.BackgroundPrimary
import com.practicum.playlistmaker.ui.theme.BluePrimary
import com.practicum.playlistmaker.ui.theme.TextPrimary

@Composable
fun DeleteAlertDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    objectToDelete: Any
) {
    val text = when (objectToDelete) {
        is Track -> stringResource(R.string.delete_track_confirmation, objectToDelete.trackName)
        is Playlist -> stringResource(R.string.delete_playlist_confirmation, objectToDelete.name)
        else -> ""
    }
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(
                    stringResource(R.string.yes),
                    style = MaterialTheme.typography.bodyMedium,
                    color = BluePrimary
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    stringResource(R.string.no),
                    style = MaterialTheme.typography.bodyMedium,
                    color = BluePrimary
                )
            }
        },
        text = { Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = TextPrimary
        )},
        containerColor = BackgroundPrimary,
    )
}