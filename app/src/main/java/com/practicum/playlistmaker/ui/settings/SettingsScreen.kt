package com.practicum.playlistmaker.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.ui.common.CustomTopBar
import com.practicum.playlistmaker.ui.settings.components.SettingsRowItem
import com.practicum.playlistmaker.ui.settings.components.SettingsSwitchRowItem
import com.practicum.playlistmaker.ui.theme.BackgroundPrimary
import com.practicum.playlistmaker.ui.settings.helpers.contactSupport
import com.practicum.playlistmaker.ui.settings.helpers.openUserAgreement
import com.practicum.playlistmaker.ui.settings.helpers.rememberSettingsStrings
import com.practicum.playlistmaker.ui.settings.helpers.shareApp

@Preview(device = "id:pixel_5", showSystemUi = true, name = "settings-preview")
@Composable
fun SettingsScreen(onBackClick: () -> Unit = {}) {
    val context = LocalContext.current
    var isDarkTheme by remember { mutableStateOf(false) }
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
                onCheckedChange = { isDarkTheme = it }
            )
            SettingsRowItem(
                title = stringResource(R.string.share_app),
                trailingIcon = Icons.Filled.Share
            ) {
                shareApp(
                    context,
                    strings.shareMessage,
                    strings.shareVia
                )
            }
            SettingsRowItem(
                title = stringResource(R.string.write_to_support),
                trailingIcon = Icons.Outlined.SupportAgent
            ) {
                contactSupport(
                    context,
                    strings.supportEmail,
                    strings.supportSubject,
                    strings.supportBody
                )
            }
            SettingsRowItem(
                title = stringResource(R.string.user_agreement),
                trailingIcon = Icons.Outlined.ChevronRight
            ) {
                openUserAgreement(
                    context,
                    strings.userAgreementLink
                )
            }
        }
    }
}