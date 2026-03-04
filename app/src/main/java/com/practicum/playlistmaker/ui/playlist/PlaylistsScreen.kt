package com.practicum.playlistmaker.ui.playlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.domain.model.Playlist
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.ui.common.CustomTopBar
import com.practicum.playlistmaker.ui.playlist.components.PlaylistListItem
import com.practicum.playlistmaker.ui.playlist.components.AddFloatingButton
import com.practicum.playlistmaker.ui.playlist.viewModel.PlaylistsViewModel
import com.practicum.playlistmaker.ui.theme.BackgroundPrimary
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun PlaylistsScreen(
    playlistsViewModel: PlaylistsViewModel,
    addNewPlaylist: () -> Unit,
    navigateToPlaylist: (Long) -> Unit,
    onBackClick: () -> Unit
) {
    val playlists by playlistsViewModel.playlists.collectAsState(emptyList())

    Scaffold(
        topBar = {
            CustomTopBar(
                text = stringResource(R.string.playlists_title),
                onBackClick = onBackClick,
                showBackButton = true
            )
        },
        containerColor = BackgroundPrimary
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .fillMaxSize()
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(playlists.size) { index ->
                    PlaylistListItem(playlist = playlists[index]) {
                        navigateToPlaylist(index.toLong())
                    }
                }
            }
            AddFloatingButton {
                addNewPlaylist()
            }
        }
    }
}

// Функция предпросмотра с тестовыми плейлистами
/*@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PlaylistsScreenPreview() {
    // Создаем тестовые треки
    val sampleTracks = listOf(
        Track(
            id = 1,
            trackName = "Владивосток 2000",
            artistName = "Мумий Троль",
            trackTime = "2:38",
            image = "",
            favorite = false,
            playlistId = 1
        ),
        Track(
            id = 2,
            trackName = "Группа крови",
            artistName = "Кино",
            trackTime = "4:43",
            image = "",
            favorite = false,
            playlistId = 2
        )
    )

    // Создаем тестовые плейлисты
    val samplePlaylists = listOf(
        Playlist(
            id = 1,
            name = "Русский рок",
            description = "Лучшие песни русского рока",
            tracks = sampleTracks
        ),
        Playlist(
            id = 2,
            name = "Для тренировок",
            description = "Энергичная музыка для спорта",
            tracks = emptyList()
        ),
        Playlist(
            id = 3,
            name = "В дорогу",
            description = "Плейлист для дальних поездок",
            tracks = sampleTracks
        ),
        Playlist(
            id = 4,
            name = "Спокойной ночи",
            description = "Расслабляющая музыка перед сном",
            tracks = emptyList()
        )
    )

    // Создаем мок ViewModel с тестовыми данными
    val mockViewModel = object : PlaylistsViewModel() {
        override val playlists = MutableStateFlow(samplePlaylists)
    }

    PlaylistsScreen(
        playlistsViewModel = mockViewModel,
        addNewPlaylist = {},
        navigateToPlaylist = {},
        onBackClick = {}
    )
}*/