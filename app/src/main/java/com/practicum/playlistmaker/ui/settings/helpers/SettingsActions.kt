package com.practicum.playlistmaker.ui.settings.helpers

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

fun shareApp(context: Context, message: String, title: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    context.startActivity(Intent.createChooser(intent, title))
}

fun contactSupport(context: Context, email: String, subject: String, body: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = "mailto:".toUri()
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
    }
    context.startActivity(intent)
}

fun openUserAgreement(context: Context, link: String) {
    val intent = Intent(Intent.ACTION_VIEW, link.toUri())
    context.startActivity(intent)
}