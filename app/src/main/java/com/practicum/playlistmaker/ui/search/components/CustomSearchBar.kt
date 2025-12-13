package com.practicum.playlistmaker.ui.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.ui.theme.BackgroundSecondary
import com.practicum.playlistmaker.ui.theme.BluePrimary
import com.practicum.playlistmaker.ui.theme.IconHint
import com.practicum.playlistmaker.ui.theme.TextHint
import com.practicum.playlistmaker.ui.theme.TextPrimary

@Composable
fun SearchBarCustom(
    query: String,
    onQueryChange: (String) -> Unit,
    onClearClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(36.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = BackgroundSecondary)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            modifier = Modifier.size(19.dp).clickable { onSearchClick() },
            tint = IconHint
        )

        Spacer(modifier = Modifier.width(8.dp))

        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = TextPrimary
            ),
            cursorBrush = SolidColor(BluePrimary),
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 0.dp)
        ) { innerTextField ->
            if (query.isEmpty()) {
                Text(
                    text = stringResource(R.string.search_hint),
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextHint
                )
            }
            innerTextField()
        }

        if (query.isNotEmpty()) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Clear",
                modifier = Modifier
                    .size(19.dp)
                    .clickable { onClearClick() },
                tint = IconHint
            )
        }
    }
}