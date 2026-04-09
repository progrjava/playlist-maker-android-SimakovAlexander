package com.practicum.playlistmaker.ui.newPlaylist.viewModel

import android.content.Context
import android.os.Environment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.domain.api.PlaylistsRepository
import com.practicum.playlistmaker.domain.model.Playlist
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import androidx.core.net.toUri
import java.io.File
import java.io.FileOutputStream

class NewPlaylistViewModel(
    private val playlistsRepository: PlaylistsRepository,
    private val playlistId: Long?
) : ViewModel() {

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()

    private val _imageUri = MutableStateFlow<String?>(null)
    val imageUri: StateFlow<String?> = _imageUri.asStateFlow()

    private var originalPlaylist: Playlist? = null

    init {
        if (playlistId != null) {
            viewModelScope.launch {
                playlistsRepository.getPlaylistById(playlistId).collect { playlist ->
                    if (playlist != null) {
                        originalPlaylist = playlist
                        _name.value = playlist.name
                        _description.value = playlist.description ?: ""
                        _imageUri.value = playlist.image
                    }
                }
            }
        }
    }

    fun onNameChanged(newName: String) { _name.value = newName }
    fun onDescriptionChanged(newDescription: String) { _description.value = newDescription }
    fun onImageChanged(newUri: String?) { _imageUri.value = newUri }

    private fun saveImageToInternalStorage(context: Context, uriString: String?): String? {
        if (uriString == null) return null
        val uri = uriString.toUri()

        if (uri.scheme != "content") return uriString

        val inputStream = context.contentResolver.openInputStream(uri)

        val filePath = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "playlist_covers")
        if (!filePath.exists()) filePath.mkdirs()

        val file = File(filePath, "cover_${System.currentTimeMillis()}.jpg")

        inputStream?.use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
        }
        return file.absolutePath
    }

    private fun deleteImageFile(path: String) {
        try {
            val file = File(path)
            if (file.exists()) {
                file.delete()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onSaveClick(context: Context) {
        viewModelScope.launch {
            val currentName = _name.value
            val currentDescription = _description.value
            val currentUri = _imageUri.value

            val oldImagePath = originalPlaylist?.image
            val finalImagePath = saveImageToInternalStorage(context, currentUri)

            if (playlistId == null) {
                val newPlaylist = Playlist(
                    name = currentName,
                    description = currentDescription,
                    image = finalImagePath,
                )
                playlistsRepository.createPlaylist(newPlaylist)
            } else {
                originalPlaylist?.let {
                    val updatedPlaylist = it.copy(
                        name = currentName,
                        description = currentDescription,
                        image = finalImagePath
                    )
                    playlistsRepository.updatePlaylist(updatedPlaylist)

                    if (oldImagePath != null && oldImagePath != finalImagePath) {
                        deleteImageFile(oldImagePath)
                    }
                }
            }
        }
    }
}
