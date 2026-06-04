package com.practicum.playlistmaker.ui.settings.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.data.preferences.ThemePreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.practicum.playlistmaker.ui.settings.helpers.shareApp
import com.practicum.playlistmaker.ui.settings.helpers.contactSupport
import com.practicum.playlistmaker.ui.settings.helpers.openUserAgreement
import com.practicum.playlistmaker.ui.settings.helpers.SettingsStrings

class SettingsViewModel(private val themePreferences: ThemePreferences) : ViewModel() {

    private val _isDarkTheme = MutableStateFlow<Boolean?>(false)
    val isDarkTheme = _isDarkTheme.asStateFlow()

    init {
        viewModelScope.launch {
            themePreferences.isDarkTheme.collect { isDark ->
                _isDarkTheme.value = isDark
            }
        }
    }

    fun onThemeChanged(isDark: Boolean?) {
        viewModelScope.launch {
            themePreferences.saveThemePreference(isDark)
        }
    }

    fun shareApp(context: Context, strings: SettingsStrings) {
        shareApp(context, strings.shareMessage, strings.shareVia)
    }

    fun contactSupport(context: Context, strings: SettingsStrings) {
        contactSupport(context, strings.supportEmail, strings.supportSubject, strings.supportBody)
    }

    fun openUserAgreement(context: Context, strings: SettingsStrings) {
        openUserAgreement(context, strings.userAgreementLink)
    }
}