package com.practicum.playlistmaker.ui.main.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.practicum.playlistmaker.ui.common.RowItem

@Composable
fun MainScreenRowItem(
    title: String,
    leadingIcon: ImageVector?,
    onClick: () -> Unit
) {
    RowItem(
        modifier = Modifier
            .fillMaxWidth()
            .height(66.dp)
            .padding(horizontal = 12.dp + 16.dp),
        title = title,
        textStyle = MaterialTheme.typography.titleLarge,
        leadingIcon = leadingIcon,
        onClick = onClick
    )
}