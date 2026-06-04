package com.practicum.playlistmaker.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.themeDataStore by preferencesDataStore(name = "theme_preferences")

class ThemePreferences(private val context: Context) {

    companion object {
        val IS_DARK_THEME_KEY = booleanPreferencesKey("is_dark_theme")
    }

    val isDarkTheme: Flow<Boolean?> = context.themeDataStore.data
        .map { preferences ->
            preferences[IS_DARK_THEME_KEY]
        }

    suspend fun saveThemePreference(isDark: Boolean?) {
        context.themeDataStore.edit { preferences ->
            if (isDark == null) {
                preferences.remove(IS_DARK_THEME_KEY)
            } else {
                preferences[IS_DARK_THEME_KEY] = isDark
            }
        }
    }
}