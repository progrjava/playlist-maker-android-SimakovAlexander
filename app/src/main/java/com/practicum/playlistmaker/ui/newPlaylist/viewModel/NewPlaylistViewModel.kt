package com.practicum.playlistmaker.ui.newPlaylist.viewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.domain.api.PlaylistsRepository
import com.practicum.playlistmaker.domain.model.Playlist
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import androidx.core.net.toUri

class NewPlaylistViewModel(
    private val playlistsRepository: PlaylistsRepository,
    private val playlistId: Long?
) : ViewModel() {

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()

    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri: StateFlow<Uri?> = _imageUri.asStateFlow()

    private var originalPlaylist: Playlist? = null

    init {
        if (playlistId != null) {
            viewModelScope.launch {
                playlistsRepository.getPlaylistById(playlistId).collect { playlist ->
                    if (playlist != null) {
                        originalPlaylist = playlist
                        _name.value = playlist.name
                        _description.value = playlist.description ?: ""
                        _imageUri.value = playlist.image?.toUri()
                    }
                }
            }
        }
    }

    fun onNameChanged(newName: String) { _name.value = newName }
    fun onDescriptionChanged(newDescription: String) { _description.value = newDescription }
    fun onImageChanged(newUri: Uri?) { _imageUri.value = newUri }

    fun onSaveClick() {
        viewModelScope.launch {
            val currentName = _name.value
            val currentDescription = _description.value
            val currentImagePath = _imageUri.value?.toString()

            if (playlistId == null) {
                val newPlaylist = Playlist(
                    name = currentName,
                    description = currentDescription,
                    image = currentImagePath,
                )
                playlistsRepository.createPlaylist(newPlaylist)
            } else {
                originalPlaylist?.let {
                    val updatedPlaylist = it.copy(
                        name = currentName,
                        description = currentDescription,
                        image = currentImagePath
                    )
                    playlistsRepository.updatePlaylist(updatedPlaylist)
                }
            }
        }
    }
}
