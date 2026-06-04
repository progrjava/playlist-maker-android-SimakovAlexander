package com.practicum.playlistmaker.domain.api

import com.practicum.playlistmaker.domain.model.Playlist
import com.practicum.playlistmaker.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface PlaylistsRepository {
    suspend fun createPlaylist(playlist: Playlist)
    suspend fun addTrackToPlaylist(playlistId: Long, track: Track)
    suspend fun removeTrackFromPlaylist(playlistId: Long, track: Track)
    suspend fun deletePlaylist(playlist: Playlist)
    fun getPlaylists(): Flow<List<Playlist>>
    fun getPlaylistById(playlistId: Long): Flow<Playlist?>
    fun getTracksForPlaylist(playlistId: Long): Flow<List<Track>>
    suspend fun updatePlaylist(playlist: Playlist)
    fun getTracksCountForPlaylist(playlistId: Long): Flow<Int>
}