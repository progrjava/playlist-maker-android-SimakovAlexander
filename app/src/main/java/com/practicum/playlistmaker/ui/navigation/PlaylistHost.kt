package com.practicum.playlistmaker.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.ui.favorites.FavoritesScreen
import com.practicum.playlistmaker.ui.main.MainScreen
import com.practicum.playlistmaker.ui.navigation.helpers.NavRoutes
import com.practicum.playlistmaker.ui.playlist.NewPlaylistScreen
import com.practicum.playlistmaker.ui.playlist.PlaylistsScreen
import com.practicum.playlistmaker.ui.playlist.viewModel.PlaylistsViewModel
import com.practicum.playlistmaker.ui.search.SearchScreen
import com.practicum.playlistmaker.ui.search.viewModel.SearchViewModel
import com.practicum.playlistmaker.ui.settings.SettingsScreen
import com.practicum.playlistmaker.ui.trackDetails.TrackDetails

@Composable
fun PlaylistHost(navController: NavHostController) {
    val searchViewModel: SearchViewModel = viewModel()
    val playlistsViewModel: PlaylistsViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.MAIN.route,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { 1000 }, // справа
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -300 }, // влево
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -1000 }, // слева (назад)
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { 300 }, // вправо
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        }
    ) {
        composable(Screen.MAIN.route) {
            MainScreen(
                onSearchClick = {
                    navController.navigate(Screen.SEARCH.route)
                },
                onPlaylistsClick = {
                    navController.navigate(Screen.PLAYLISTS.route)
                },
                onFavoritesClick = {
                    navController.navigate(Screen.FAVORITES.route)
                },
                onSettingsClick = {
                    navController.navigate(Screen.SETTINGS.route)
                }
            )
        }

        composable(Screen.SEARCH.route) {
            SearchScreen(
                searchViewModel = searchViewModel,
                onTrackClick = { track ->
                    navController.navigate(NavRoutes.trackDetails(track.trackId))
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            NavRoutes.TRACK_DETAILS,
            arguments = listOf(navArgument("trackId") {type = NavType.LongType})
        ) { backStackEntry ->
            val trackId = backStackEntry.arguments?.getLong("trackId") ?: return@composable
            var track by remember { mutableStateOf<Track?>(null) }

            LaunchedEffect(trackId) {
                track = playlistsViewModel.getTrackById(trackId)
            }

            track?.let { currentTrack ->
                TrackDetails(
                    track = currentTrack,
                    playlistsViewModel = playlistsViewModel,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }

        composable(Screen.SETTINGS.route) {
            SettingsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.PLAYLISTS.route) {
            PlaylistsScreen(
                playlistsViewModel = playlistsViewModel,
                addNewPlaylist = {
                    navController.navigate(Screen.NEW_PLAYLIST.route)
                },
                navigateToPlaylist = { playlistId ->
                    // позже
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.NEW_PLAYLIST.route) {
            NewPlaylistScreen(
                onCreate = { namePlaylist, descriptionPlaylist ->
                    playlistsViewModel.createNewPlaylist(namePlaylist, descriptionPlaylist)
                    navController.popBackStack()
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.FAVORITES.route) {
            FavoritesScreen(
                playlistsViewModel = playlistsViewModel,
                onTrackClick = { track ->
                    navController.navigate(NavRoutes.trackDetails(track.trackId))
                },
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}