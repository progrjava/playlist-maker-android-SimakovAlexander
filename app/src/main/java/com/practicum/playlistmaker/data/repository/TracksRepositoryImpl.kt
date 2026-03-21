package com.practicum.playlistmaker.data.repository

import com.practicum.playlistmaker.data.local.DatabaseMock
import com.practicum.playlistmaker.data.dto.TracksSearchRequest
import com.practicum.playlistmaker.data.dto.TracksSearchResponse
import com.practicum.playlistmaker.data.mapper.TrackMapper.map
import com.practicum.playlistmaker.domain.api.NetworkClient
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.domain.api.TracksRepository
import kotlinx.coroutines.flow.Flow

class TracksRepositoryImpl(
    private val database: DatabaseMock,
    private val networkClient: NetworkClient
) : TracksRepository {

    override suspend fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(TracksSearchRequest(expression))

        return if (response is TracksSearchResponse) {
            response.results.map { it.map() }
        } else {
            throw Exception(response.errorMessage ?: "Unexpected response type from NetworkClient")
        }
    }

    override fun getTrackByNameAndArtist(track: Track): Flow<Track?> {
        return database.getTrackByNameAndArtist(track)
    }

    override suspend fun insertTrackToPlaylist(
        track: Track,
        playlistId: Long
    ) {
        database.insertTrack(track.copy(playlistId = playlistId))
    }

    override suspend fun deleteTrackFromPlaylist(track: Track) {
        database.deleteTrackFromPlaylist(track.trackId)
    }

    override suspend fun updateTrackFavoriteStatus(
        track: Track,
        isFavorite: Boolean
    ) {
        database.insertTrack(track.copy(favorite = isFavorite))
    }

    override suspend fun getTrackById(trackId: Long): Track? {
        return database.getTrackById(trackId)
    }

    override fun deleteTracksByPlaylistId(playlistId: Long) {
        database.deleteTracksByPlaylistId(playlistId)
    }

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return database.getFavoriteTracks()
    }

    override suspend fun insertTrack(track: Track) {
        database.insertTrack(track)
    }
}
