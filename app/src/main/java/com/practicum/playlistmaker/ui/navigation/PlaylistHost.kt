package com.practicum.playlistmaker.ui.navigation

import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.practicum.playlistmaker.ui.playlist.PlaylistsScreen
import com.practicum.playlistmaker.ui.main.MainScreen
import com.practicum.playlistmaker.ui.search.SearchScreen
import com.practicum.playlistmaker.ui.search.viewModel.SearchViewModel
import com.practicum.playlistmaker.ui.settings.SettingsScreen

@Composable
fun PlaylistHost(navController: NavHostController) {
    val context = LocalContext.current
    val searchViewModel: SearchViewModel = viewModel()

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
                    Toast.makeText(
                        context,
                        "Нажата кнопка \"Избранное\"",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                onSettingsClick = {
                    navController.navigate(Screen.SETTINGS.route)
                }
            )
        }

        composable(Screen.SEARCH.route) {
            SearchScreen(
                searchViewModel = searchViewModel,
                onClick = {  },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.SETTINGS.route) {
            SettingsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.PLAYLISTS.route) {
            PlaylistsScreen()
        }
    }
}