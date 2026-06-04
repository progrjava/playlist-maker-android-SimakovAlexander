package com.practicum.playlistmaker.data.mapper

import com.practicum.playlistmaker.data.dto.TrackDto
import com.practicum.playlistmaker.domain.model.Track
import java.text.SimpleDateFormat
import java.util.Locale

object TrackMapper {
    fun TrackDto.map(): Track {
        val trackTime = SimpleDateFormat("mm:ss", Locale.getDefault()).format(trackTimeMillis)
        val imageUrl = image?.replaceAfterLast('/', "512x512bb.jpg") ?: ""
        return Track(
            trackId = id,
            trackName = trackName,
            artistName = artistName,
            trackTime = trackTime,
            image = imageUrl,
            favorite = false,
        )
    }
}