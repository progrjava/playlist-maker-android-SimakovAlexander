package com.practicum.playlistmaker.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.practicum.playlistmaker.R


val YandexSansFontFamily = FontFamily(
    Font(R.font.yst_bold, weight = FontWeight.Bold),
    Font(R.font.yst_thin, weight = FontWeight.Thin),
    Font(R.font.yst_light, weight = FontWeight.Light),
    Font(R.font.yst_medium, weight = FontWeight.Medium),
    Font(R.font.yst_regular, weight = FontWeight.Normal),
    Font(R.font.yst_regular_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
)

// Set of Material typography styles to start with
val YandexSansTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = YandexSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = YandexSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = YandexSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = YandexSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = YandexSansFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 28.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = YandexSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = YandexSansFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = YandexSansFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 19.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = YandexSansFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = YandexSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = YandexSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = YandexSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = YandexSansFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = YandexSansFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = YandexSansFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
    )
)