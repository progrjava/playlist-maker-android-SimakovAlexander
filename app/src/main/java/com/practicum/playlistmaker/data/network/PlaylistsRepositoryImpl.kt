package com.practicum.playlistmaker.data.network

import com.practicum.playlistmaker.data.local.DatabaseProvider
import com.practicum.playlistmaker.domain.PlaylistsRepository
import com.practicum.playlistmaker.domain.model.Playlist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class PlaylistsRepositoryImpl(
    private val scope: CoroutineScope,
) : PlaylistsRepository {
    private val database = DatabaseProvider.getDatabase(scope)

    override fun getPlaylist(playlistId: Long): Flow<Playlist?> {
        return database.getPlayList(playlistId)
    }

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return database.getAllPlaylists()
    }

    override suspend fun addNewPlaylist(name: String, description: String) {
        database.addNewPlaylist(name, description)
    }

    override suspend fun deletePlaylistById(id: Long) {
        database.deletePlaylistById(id)
    }
}