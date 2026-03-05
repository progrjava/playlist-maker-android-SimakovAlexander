package com.practicum.playlistmaker.ui.playlist

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.ui.common.CustomTopBar
import com.practicum.playlistmaker.ui.theme.BackgroundPrimary
import com.practicum.playlistmaker.ui.theme.BluePrimary
import com.practicum.playlistmaker.ui.theme.GrayBackground
import com.practicum.playlistmaker.ui.theme.TextOnPrimary
import com.practicum.playlistmaker.ui.theme.TextPrimary

@Composable
fun NewPlaylistScreen(
    onCreate: (namePlaylist: String, descriptionPlaylist: String) -> Unit,
    onBackClick: () -> Unit
) {
    val name = rememberTextFieldState()
    val description = rememberTextFieldState()

    val isNameFilled = name.text.toString().isNotBlank()
    val isDescriptionFilled = description.text.toString().isNotBlank()

    val animatedColor = animateColorAsState(
        targetValue = if (isNameFilled) BluePrimary else GrayBackground,
        animationSpec = spring()
    )

    val animatedScale = animateFloatAsState(
        targetValue = if (isNameFilled) 1f else 0.98f,
        animationSpec = spring()
    )

    Scaffold(
        topBar = {
            CustomTopBar(
                text = stringResource(R.string.new_playlist_title),
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
                        top = innerPadding.calculateTopPadding() + 26.dp,
                        bottom = 32.dp
                    )
                    .padding(horizontal = 16.dp)
                    .imePadding()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = 32.dp,
                            start = 8.dp,
                            end = 8.dp
                        ),
                    painter = painterResource(id = R.drawable.ic_playlist),
                    contentDescription = "",
                )
                OutlinedTextField(
                    state = name,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    enabled = true,
                    readOnly = false,
                    textStyle = MaterialTheme.typography.bodyLarge,
                    label = { Text(stringResource(R.string.new_playlist_name)) },
                    isError = false,
                    lineLimits = TextFieldLineLimits.SingleLine,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    shape = RoundedCornerShape(4.dp),
                    contentPadding = OutlinedTextFieldDefaults.contentPadding(
                        start = 16.dp,
                        top = 18.dp,
                        end = 16.dp,
                        bottom = 18.dp,
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = if (isNameFilled) BluePrimary else BluePrimary,
                        unfocusedBorderColor = if (isNameFilled) BluePrimary else GrayBackground,

                        focusedLabelColor = if (isNameFilled) BluePrimary else BluePrimary,
                        unfocusedLabelColor = if (isNameFilled) BluePrimary else TextPrimary,

                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                    )
                )
                OutlinedTextField(
                    state = description,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    enabled = true,
                    readOnly = false,
                    textStyle = MaterialTheme.typography.bodyLarge,
                    label = { Text(stringResource(R.string.new_playlist_description)) },
                    isError = false,
                    lineLimits = TextFieldLineLimits.SingleLine,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    shape = RoundedCornerShape(4.dp),
                    contentPadding = OutlinedTextFieldDefaults.contentPadding(
                        start = 16.dp,
                        top = 18.dp,
                        end = 16.dp,
                        bottom = 18.dp,
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = if (isDescriptionFilled) BluePrimary else BluePrimary,
                        unfocusedBorderColor = if (isDescriptionFilled) BluePrimary else GrayBackground,

                        focusedLabelColor = if (isDescriptionFilled) BluePrimary else BluePrimary,
                        unfocusedLabelColor = if (isDescriptionFilled) BluePrimary else TextPrimary,

                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        onCreate(name.text.toString(), description.text.toString())
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer {
                            scaleX = animatedScale.value
                            scaleY = animatedScale.value
                        },
                    enabled = isNameFilled,
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(
                        vertical = 12.dp,
                        horizontal = 16.dp
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = animatedColor.value,
                        contentColor = TextOnPrimary,
                        disabledContainerColor = animatedColor.value,
                        disabledContentColor = TextOnPrimary
                    )
                ) {
                    Text(
                        stringResource(R.string.new_playlist_create),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
    }
}

/*@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun NewPlaylistScreenPreview() {

    MaterialTheme {
        NewPlaylistScreen(
            playlistsViewModel = object : PlaylistsViewModel() {
                override fun createNewPlaylist(namePlaylist: String, descriptionPlaylist: String) {
                    // ничего не делаем для preview
                }
            },
            onCreate = {},
            onBackClick = {}
        )
    }
}*/