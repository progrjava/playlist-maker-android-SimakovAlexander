package com.practicum.playlistmaker.domain

import com.practicum.playlistmaker.domain.model.Track

interface TracksRepository {
    suspend fun getAllTracks(): List<Track>
    suspend fun searchTracks(expression: String): List<Track>
}