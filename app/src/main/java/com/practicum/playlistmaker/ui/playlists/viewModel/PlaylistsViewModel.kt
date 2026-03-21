package com.practicum.playlistmaker.ui.playlists.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.domain.PlaylistsRepository
import com.practicum.playlistmaker.domain.TracksRepository
import com.practicum.playlistmaker.domain.model.Playlist
import com.practicum.playlistmaker.domain.model.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class PlaylistsViewModel(
    val playlistsRepository: PlaylistsRepository,
    private val tracksRepository: TracksRepository
) : ViewModel() {

    val playlists: Flow<List<Playlist>> = flow {
        val collectedPlaylists = mutableListOf<Playlist>()
        playlistsRepository.getAllPlaylists().collect { playlist ->
            collectedPlaylists.addAll(playlist)
            emit(collectedPlaylists.toList())
        }
    }

    val favoriteList: Flow<List<Track>> = tracksRepository.getFavoriteTracks()

    fun createNewPlaylist(namePlaylist: String, descriptionPlaylist: String) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistsRepository.addNewPlaylist(namePlaylist, descriptionPlaylist)
        }
    }

    suspend fun getTrackById(trackId: Long): Track? {
        return tracksRepository.getTrackById(trackId)
    }

    suspend fun insertTrackToPlaylist(track: Track, playlistId: Long) {
        tracksRepository.insertTrackToPlaylist(track, playlistId)
    }

    suspend fun toggleFavorite(track: Track, isFavorite: Boolean) {
        tracksRepository.updateTrackFavoriteStatus(track, isFavorite)
    }

    suspend fun deleteTrackFromPlaylist(track: Track) {
        tracksRepository.deleteTrackFromPlaylist(track)
    }

    suspend fun deletePlaylistById(id: Long) {
        tracksRepository.deleteTracksByPlaylistId(id)
        playlistsRepository.deletePlaylistById(id)
    }

    suspend fun isExist(track: Track): Track? {
        return tracksRepository.getTrackByNameAndArtist(track).firstOrNull()
    }
}
