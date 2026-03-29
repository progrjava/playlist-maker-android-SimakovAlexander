package com.practicum.playlistmaker.data.repository

import com.practicum.playlistmaker.data.database.AppDatabase
import com.practicum.playlistmaker.data.database.converter.toEntity
import com.practicum.playlistmaker.data.database.converter.toPlaylist
import com.practicum.playlistmaker.data.database.converter.toTrack
import com.practicum.playlistmaker.data.database.entity.PlaylistTrackCrossRef
import com.practicum.playlistmaker.domain.api.PlaylistsRepository
import com.practicum.playlistmaker.domain.model.Playlist
import com.practicum.playlistmaker.domain.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlaylistsRepositoryImpl(
    database: AppDatabase
) : PlaylistsRepository {

    private val playlistsDao = database.PlaylistsDao()
    private val tracksDao = database.TracksDao()
    private val playlistTrackDao = database.PlaylistTrackDao()

    override suspend fun createPlaylist(playlist: Playlist) {
        playlistsDao.insertPlaylist(playlist.toEntity())
    }

    override suspend fun addTrackToPlaylist(
        playlistId: Long,
        track: Track
    ) {
        tracksDao.insertTrack(track.toEntity())
        val count = playlistTrackDao.getTrackCountInPlaylist(playlistId, track.trackId)

        if (count == 0) {
            playlistTrackDao.insertPlaylistTrackCrossRef(
                PlaylistTrackCrossRef(playlistId = playlistId, trackId = track.trackId)
            )
        }
    }

    override suspend fun removeTrackFromPlaylist(
        playlistId: Long,
        track: Track
    ) {
        val count = playlistTrackDao.getTrackCountInPlaylist(playlistId, track.trackId)

        if (count > 0) {
            playlistTrackDao.deletePlaylistTrackCrossRef(playlistId, track.trackId)
        }
    }

    override suspend fun deletePlaylist(playlist: Playlist) {
        playlistsDao.deletePlaylist(playlist.toEntity())
        playlistTrackDao.deleteTracksForPlaylist(playlist.id)
    }

    override fun getPlaylists(): Flow<List<Playlist>> {
        return playlistsDao.getPlaylists().map { playlists ->
            playlists.map { it.toPlaylist() }
        }
    }

    override fun getPlaylistById(playlistId: Long): Flow<Playlist?> {
        return playlistsDao.getPlaylistById(playlistId).map { it?.toPlaylist() }
    }

    override fun getTracksForPlaylist(playlistId: Long): Flow<List<Track>> {
        return playlistTrackDao.getTracksForPlaylist(playlistId).map { tracks ->
            tracks.map { it.toTrack() }
        }
    }

    override suspend fun updatePlaylist(playlist: Playlist) {
        return playlistsDao.updatePlaylist(playlist.toEntity())
    }

    override fun getTracksCountForPlaylist(playlistId: Long): Flow<Int> {
        return playlistTrackDao.getTracksCountForPlaylist(playlistId)
    }
}
