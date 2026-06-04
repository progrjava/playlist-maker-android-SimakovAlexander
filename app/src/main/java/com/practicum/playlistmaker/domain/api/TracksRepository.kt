package com.practicum.playlistmaker.domain.api

import com.practicum.playlistmaker.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>
    fun getFavoriteTracks(): Flow<List<Track>>
    suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean)
    fun getTrackById(trackId: Long): Flow<Track?>
    suspend fun insertTrack(track: Track)
}