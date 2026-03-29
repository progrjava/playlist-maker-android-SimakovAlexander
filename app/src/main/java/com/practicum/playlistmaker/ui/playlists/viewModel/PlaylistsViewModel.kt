package com.practicum.playlistmaker.ui.playlists.viewModel

import androidx.lifecycle.ViewModel
import com.practicum.playlistmaker.domain.api.PlaylistsRepository
import com.practicum.playlistmaker.domain.api.TracksRepository
import com.practicum.playlistmaker.domain.model.Playlist
import com.practicum.playlistmaker.domain.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaylistsViewModel(
    val playlistsRepository: PlaylistsRepository,
    private val tracksRepository: TracksRepository
) : ViewModel() {

    val playlists: Flow<List<Playlist>> = flow {
        playlistsRepository.getPlaylists().collect { playlist ->
            emit(playlist)
        }
    }

    val favoriteList: Flow<List<Track>> = tracksRepository.getFavoriteTracks()

    fun getTrackById(trackId: Long): Flow<Track?> {
        return tracksRepository.getTrackById(trackId)
    }

    suspend fun insertTrackToPlaylist(track: Track, playlistId: Long) {
        playlistsRepository.addTrackToPlaylist(playlistId, track)
    }

    suspend fun toggleFavorite(track: Track, isFavorite: Boolean) {
        tracksRepository.updateTrackFavoriteStatus(track, isFavorite)
    }

    fun getTracksCountForPlaylist(playlistId: Long): Flow<Int> {
        return playlistsRepository.getTracksCountForPlaylist(playlistId)
    }
}
