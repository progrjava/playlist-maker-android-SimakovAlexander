package com.practicum.playlistmaker

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.practicum.playlistmaker.ui.theme.PlaylistMakerTheme
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LibraryMusic
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.practicum.playlistmaker.ui.components.common.CustomTopBar
import com.practicum.playlistmaker.ui.components.main.MenuItem
import com.practicum.playlistmaker.ui.components.main.MenuList
import com.practicum.playlistmaker.ui.theme.BackgroundBrand
import com.practicum.playlistmaker.ui.theme.BackgroundPrimary
import com.practicum.playlistmaker.ui.theme.TextOnPrimary
import com.practicum.playlistmaker.ui.theme.Transparent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaylistMakerTheme {
                val context = LocalContext.current

                MainScreen(
                    onSearchClick = {
                        val searchIntent = Intent(
                            context,
                            SearchActivity::class.java
                        )
                        context.startActivity(searchIntent)
                    },
                    onPlaylistsClick = {
                        Toast.makeText(
                            context,
                            "Нажата кнопка \"Плейлисты\"",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    },
                    onFavoritesClick = {
                        Toast.makeText(
                            context,
                            "Нажата кнопка \"Избранное\"",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    },
                    onSettingsClick = {
                        val settingsIntent = Intent(
                            context,
                            SettingsActivity::class.java
                        )
                        context.startActivity(settingsIntent)
                    }
                )
            }
        }
    }
}

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
                    MenuList(
                        menuItems = listOf(
                            MenuItem(stringResource(R.string.search_title), Icons.Outlined.Search, onSearchClick),
                            MenuItem(stringResource(R.string.playlists_title), Icons.Outlined.LibraryMusic, onPlaylistsClick),
                            MenuItem(stringResource(R.string.favourites_title), Icons.Outlined.FavoriteBorder, onFavoritesClick),
                            MenuItem(stringResource(R.string.settings_title), Icons.Outlined.Settings, onSettingsClick)
                        )
                    )
                }
            }
        }
    )
}