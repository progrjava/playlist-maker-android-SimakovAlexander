package com.practicum.playlistmaker.ui.playlist.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.ui.theme.IconSecondary

@Composable
fun AddFloatingButton(
    callback: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            modifier = Modifier
                .padding(end = 17.dp, bottom = 30.dp)
                .size(51.dp)
                .align(Alignment.BottomEnd),
            onClick = callback,
            containerColor = IconSecondary,
            contentColor = Color.White,
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(0.dp)
        ) {
            Icon(
                modifier = Modifier.size(36.dp),
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(R.string.create_something)
            )
        }
    }
}