package com.practicum.playlistmaker.ui.navigation

import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.practicum.playlistmaker.ui.screens.main.MainScreen
import com.practicum.playlistmaker.ui.screens.search.SearchScreen
import com.practicum.playlistmaker.ui.screens.settings.SettingsScreen

@Composable
fun PlaylistHost(navController: NavHostController) {
    val context = LocalContext.current

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
                    Toast.makeText(
                        context,
                        "Нажата кнопка \"Плейлисты\"",
                        Toast.LENGTH_SHORT
                    ).show()
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
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.SETTINGS.route) {
            SettingsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}