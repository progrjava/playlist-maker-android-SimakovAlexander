package com.practicum.playlistmaker.ui.settings.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val thumbOffset by animateDpAsState(
        targetValue = if (checked) 17.dp else (-3).dp,
        animationSpec = tween(durationMillis = 150),
        label = "thumbOffset"
    )

    Box(
        modifier = Modifier
            .width(32.dp)
            .height(24.dp)
            .clickable { onCheckedChange(!checked) },
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .width(32.dp)
                .height(12.dp)
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(6.dp)
                )
        )

        Box(
            modifier = Modifier
                .absoluteOffset(x = thumbOffset)
                .size(18.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
        )
    }
}
