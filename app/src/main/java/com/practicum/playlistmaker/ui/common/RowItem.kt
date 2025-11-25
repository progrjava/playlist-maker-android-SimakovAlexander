package com.practicum.playlistmaker.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.practicum.playlistmaker.ui.theme.IconPrimary
import com.practicum.playlistmaker.ui.theme.IconSecondary
import com.practicum.playlistmaker.ui.theme.TextPrimary

@Composable
fun RowItem(
    modifier: Modifier = Modifier,
    title: String,
    textStyle: TextStyle,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = Icons.Outlined.ChevronRight,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = IconPrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            Text(
                text = title,
                style = textStyle,
                modifier = Modifier.weight(1f),
                color = TextPrimary
            )

            if (trailingIcon != null) {
                Icon(
                    imageVector = trailingIcon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = IconSecondary
                )
            }
        }
    }
}