package com.practicum.playlistmaker.ui.helpers

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.practicum.playlistmaker.R

data class SettingsStrings(
    val shareMessage: String,
    val shareVia: String,
    val supportEmail: String,
    val supportSubject: String,
    val supportBody: String,
    val userAgreementLink: String,
    val darkModeLabel: String,
    val settingsTitle: String,
    val shareAppTitle: String,
    val supportTitle: String,
    val agreementTitle: String
)

@Composable
fun rememberSettingsStrings(): SettingsStrings {
    return SettingsStrings(
        shareMessage = stringResource(R.string.share_message),
        shareVia = stringResource(R.string.share_via),
        supportEmail = stringResource(R.string.support_email),
        supportSubject = stringResource(R.string.support_subject),
        supportBody = stringResource(R.string.support_body),
        userAgreementLink = stringResource(R.string.user_agreement_link),
        darkModeLabel = stringResource(R.string.dark_mode_switch),
        settingsTitle = stringResource(R.string.settings_title),
        shareAppTitle = stringResource(R.string.share_app),
        supportTitle = stringResource(R.string.write_to_support),
        agreementTitle = stringResource(R.string.user_agreement)
    )
}