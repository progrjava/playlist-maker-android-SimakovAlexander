package com.practicum.playlistmaker.ui.newPlaylist.screen

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.SubcomposeAsyncImage
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.ui.common.CircularProgressIndicator
import com.practicum.playlistmaker.ui.common.CustomTopBar
import com.practicum.playlistmaker.ui.newPlaylist.viewModel.NewPlaylistViewModel

@Composable
fun NewPlaylistScreen(
    playlistId: Long? = null,
    viewModel: NewPlaylistViewModel,
    onBackClick: () -> Unit
) {
    val isEditMode = playlistId != null

    val nameState by viewModel.name.collectAsState()
    val descriptionState by viewModel.description.collectAsState()
    val imageUriState by viewModel.imageUri.collectAsState()
    val context = LocalContext.current

    val screenTitle = if (isEditMode)
        stringResource(R.string.edit_playlist_title)
    else
        stringResource(R.string.new_playlist_title)

    val buttonText = if (isEditMode)
        stringResource(R.string.save_playlist)
    else
        stringResource(R.string.new_playlist_create)

    val name = rememberTextFieldState()
    val description = rememberTextFieldState()

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.onImageChanged(it.toString())
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            imagePickerLauncher.launch("image/*")
        }
    }

    LaunchedEffect(nameState, descriptionState) {
        if (isEditMode) {
            name.edit {
                if (toString() != nameState) replace(0, length, nameState)
            }
            description.edit {
                if (toString() != descriptionState) replace(0, length, descriptionState)
            }
        }
    }

    val bluePrimary = MaterialTheme.colorScheme.primary

    val isNameFilled = name.text.toString().isNotBlank()
    val isDescriptionFilled = description.text.toString().isNotBlank()

    val animatedColor = animateColorAsState(
        targetValue = if (isNameFilled) bluePrimary else MaterialTheme.colorScheme.outline,
        animationSpec = spring()
    )

    val animatedScale = animateFloatAsState(
        targetValue = if (isNameFilled) 1f else 0.98f,
        animationSpec = spring()
    )

    Scaffold(
        topBar = {
            CustomTopBar(
                text = screenTitle,
                onBackClick = onBackClick,
                showBackButton = true
            )
        },
        containerColor = MaterialTheme.colorScheme.background
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = 32.dp,
                            start = 8.dp,
                            end = 8.dp
                        )
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                imagePickerLauncher.launch("image/*")
                            } else {
                                when {
                                    ContextCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.READ_EXTERNAL_STORAGE
                                    ) == PackageManager.PERMISSION_GRANTED -> {
                                        imagePickerLauncher.launch("image/*")
                                    }

                                    else -> {
                                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                                    }
                                }
                            }
                        }
                ) {
                    SubcomposeAsyncImage(
                        model = imageUriState,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        contentDescription = stringResource(R.string.playlist_cover),
                        loading = {
                            Box(contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        },
                        error = {
                            Image(
                                painter = painterResource(id = R.drawable.ic_playlist),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    )
                }
                OutlinedTextField(
                    state = name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
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
                        focusedBorderColor = bluePrimary,
                        unfocusedBorderColor = if (isNameFilled) bluePrimary else MaterialTheme.colorScheme.outline,

                        focusedLabelColor = bluePrimary,
                        unfocusedLabelColor = if (isNameFilled) bluePrimary else MaterialTheme.colorScheme.onBackground,

                        focusedTextColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                    )
                )
                OutlinedTextField(
                    state = description,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
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
                        focusedBorderColor = bluePrimary,
                        unfocusedBorderColor = if (isDescriptionFilled) bluePrimary else MaterialTheme.colorScheme.outline,

                        focusedLabelColor = bluePrimary,
                        unfocusedLabelColor = if (isDescriptionFilled) bluePrimary else MaterialTheme.colorScheme.onBackground,

                        focusedTextColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        viewModel.onNameChanged(name.text.toString())
                        viewModel.onDescriptionChanged(description.text.toString())
                        viewModel.onSaveClick(context)
                        onBackClick()
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
                        contentColor = MaterialTheme.colorScheme.onBackground,
                        disabledContainerColor = animatedColor.value,
                        disabledContentColor = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    Text(
                        text = buttonText,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
    }
}