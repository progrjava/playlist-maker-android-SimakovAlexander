package com.practicum.playlistmaker.ui.settings.components

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
fun SettingsRowItem(
    title: String,
    trailingIcon: ImageVector? = null,
    onClick: () -> Unit
) {
    RowItem(
        modifier = Modifier
            .fillMaxWidth()
            .height(61.dp)
            .padding(start = 16.dp, end = 12.dp),
        title = title,
        textStyle = MaterialTheme.typography.bodyLarge,
        trailingIcon = trailingIcon,
        onClick = onClick
    )
}