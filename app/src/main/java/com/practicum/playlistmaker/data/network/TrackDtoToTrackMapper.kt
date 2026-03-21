package com.practicum.playlistmaker.data.network

import com.practicum.playlistmaker.data.dto.TrackDto
import com.practicum.playlistmaker.domain.model.Track
import java.text.SimpleDateFormat
import java.util.Locale

object TrackDtoToTrackMapper {
    fun TrackDto.map(): Track {
        val trackTime = SimpleDateFormat("mm:ss", Locale.getDefault()).format(trackTimeMillis)
        val imageUrl = image?.replaceAfterLast('/', "512x512bb.jpg") ?: "" // Добавляем заглушку для null
        return Track(
            trackId = id,
            trackName = trackName,
            artistName = artistName,
            trackTime = trackTime,
            image = imageUrl,
            favorite = false, // По умолчанию false для треков из поиска
            playlistId = 0L // По умолчанию 0L для треков из поиска
        )
    }
}