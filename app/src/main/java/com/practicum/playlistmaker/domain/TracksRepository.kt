package com.practicum.playlistmaker.domain

import com.practicum.playlistmaker.data.network.Track

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>
}