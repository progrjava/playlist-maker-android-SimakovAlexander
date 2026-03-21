package com.practicum.playlistmaker.ui.main.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.ui.common.CustomTopBar
import com.practicum.playlistmaker.ui.main.components.MainScreenRowItem
import com.practicum.playlistmaker.ui.theme.BackgroundBrand
import com.practicum.playlistmaker.ui.theme.BackgroundPrimary
import com.practicum.playlistmaker.ui.theme.TextOnPrimary
import com.practicum.playlistmaker.ui.theme.Transparent

@Preview(device = "id:pixel_5", showSystemUi = true, name = "main-preview")
@Composable
fun MainScreen(
    onSearchClick: () -> Unit = {},
    onPlaylistsClick: () -> Unit = {},
    onFavoritesClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            CustomTopBar(
                text = stringResource(R.string.app_name),
                barColor = BackgroundBrand,
                contentColor = TextOnPrimary
            )
        },
        containerColor = Transparent,
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = innerPadding.calculateTopPadding())
                    .background(color = BackgroundBrand)
            ) {
                Surface(
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    color = BackgroundPrimary,
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.TopCenter)
                        .padding(top = 14.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 8.dp)
                    ) {
                        MainScreenRowItem(
                            title = stringResource(R.string.search_title),
                            leadingIcon = Icons.Outlined.Search,
                            onClick = onSearchClick
                        )
                        MainScreenRowItem(
                            title = stringResource(R.string.playlists_title),
                            leadingIcon = Icons.Filled.LibraryMusic,
                            onClick = onPlaylistsClick
                        )
                        MainScreenRowItem(
                            title = stringResource(R.string.favourites_title),
                            leadingIcon = Icons.Outlined.FavoriteBorder,
                            onClick = onFavoritesClick
                        )
                        MainScreenRowItem(
                            title = stringResource(R.string.settings_title),
                            leadingIcon = Icons.Filled.Settings,
                            onClick = onSettingsClick
                        )
                    }
                }
            }
        }
    )
}