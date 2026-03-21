package com.practicum.playlistmaker.domain

import com.practicum.playlistmaker.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>
    /*suspend fun getAllTracks(): List<Track>*/
    fun getTrackByNameAndArtist(track: Track): Flow<Track?>
    fun getFavoriteTracks(): Flow<List<Track>>
    suspend fun insertTrackToPlaylist(track: Track, playlistId: Long)
    suspend fun deleteTrackFromPlaylist(track: Track)
    fun deleteTracksByPlaylistId(playlistId: Long)
    suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean)
    suspend fun getTrackById(trackId: Long): Track?

    suspend fun insertTrack(track: Track)
}