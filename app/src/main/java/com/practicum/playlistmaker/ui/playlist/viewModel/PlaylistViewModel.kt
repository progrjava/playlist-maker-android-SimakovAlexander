package com.practicum.playlistmaker.ui.playlist.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.domain.api.PlaylistsRepository
import com.practicum.playlistmaker.domain.model.Playlist
import com.practicum.playlistmaker.domain.model.Track
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlaylistViewModel(
    private val playlistsRepository: PlaylistsRepository,
    private val playlistId: Long
) : ViewModel() {

    private val _playlist = MutableStateFlow<Playlist?>(null)
    val playlist: StateFlow<Playlist?> = _playlist.asStateFlow()

    private val _tracks = MutableStateFlow<List<Track>>(emptyList())
    val tracks: StateFlow<List<Track>> = _tracks.asStateFlow()

    init {
        loadPlaylist()
        loadTracks()
    }

    private fun loadPlaylist() {
        viewModelScope.launch {
            playlistsRepository.getPlaylistById(playlistId).collect { playlist ->
                _playlist.value = playlist
            }
        }
    }

    private fun loadTracks() {
        viewModelScope.launch {
            playlistsRepository.getTracksForPlaylist(playlistId).collect { trackList ->
                _tracks.value = trackList
            }
        }
    }

    fun removeTrack(track: Track) {
        viewModelScope.launch {
            playlistsRepository.removeTrackFromPlaylist(playlistId, track)
        }
    }

    fun deletePlaylist() {
        viewModelScope.launch {
            playlist.value?.let { playlistsRepository.deletePlaylist(it) }
        }
    }
}
