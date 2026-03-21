package com.practicum.playlistmaker.ui.settings.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.ui.common.CustomTopBar
import com.practicum.playlistmaker.ui.settings.components.SettingsRowItem
import com.practicum.playlistmaker.ui.settings.components.SettingsSwitchRowItem
import com.practicum.playlistmaker.ui.settings.viewModel.SettingsViewModel
import com.practicum.playlistmaker.ui.theme.BackgroundPrimary
import com.practicum.playlistmaker.ui.settings.helpers.rememberSettingsStrings
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit = {},
    viewModel: SettingsViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    val strings = rememberSettingsStrings()

    Scaffold(
        topBar = {
            CustomTopBar(
                text = stringResource(R.string.settings_title),
                onBackClick = onBackClick,
                showBackButton = true
            )
        },
        containerColor = BackgroundPrimary
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding() + 24.dp)
        ) {
            SettingsSwitchRowItem(
                title = stringResource(R.string.dark_mode_switch),
                isChecked = isDarkTheme,
                onCheckedChange = { viewModel.onThemeChanged(it) }
            )
            SettingsRowItem(
                title = stringResource(R.string.share_app),
                trailingIcon = Icons.Filled.Share
            ) {
                viewModel.shareApp(context, strings)
            }
            SettingsRowItem(
                title = stringResource(R.string.write_to_support),
                trailingIcon = Icons.Outlined.SupportAgent
            ) {
                viewModel.contactSupport(context, strings)
            }
            SettingsRowItem(
                title = stringResource(R.string.user_agreement),
                trailingIcon = Icons.Outlined.ChevronRight
            ) {
                viewModel.openUserAgreement(context, strings)
            }
        }
    }
}
