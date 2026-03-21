package com.practicum.playlistmaker.ui.settings.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.practicum.playlistmaker.ui.settings.helpers.shareApp
import com.practicum.playlistmaker.ui.settings.helpers.contactSupport
import com.practicum.playlistmaker.ui.settings.helpers.openUserAgreement
import com.practicum.playlistmaker.ui.settings.helpers.SettingsStrings

class SettingsViewModel : ViewModel() {

    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme = _isDarkTheme.asStateFlow()

    fun onThemeChanged(isDark: Boolean) {
        _isDarkTheme.value = isDark
        // Сохранение темы в SharedPreferences
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
