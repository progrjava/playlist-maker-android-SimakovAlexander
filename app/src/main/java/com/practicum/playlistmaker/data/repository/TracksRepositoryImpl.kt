package com.practicum.playlistmaker.data.repository

import com.practicum.playlistmaker.data.database.AppDatabase
import com.practicum.playlistmaker.data.database.converter.toEntity
import com.practicum.playlistmaker.data.database.converter.toTrack
import com.practicum.playlistmaker.data.dto.TracksSearchRequest
import com.practicum.playlistmaker.data.dto.TracksSearchResponse
import com.practicum.playlistmaker.data.mapper.TrackMapper.map
import com.practicum.playlistmaker.domain.api.NetworkClient
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.domain.api.TracksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TracksRepositoryImpl(
    database: AppDatabase,
    private val networkClient: NetworkClient
) : TracksRepository {

    private val dao = database.TracksDao()

    override suspend fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(TracksSearchRequest(expression))

        return if (response is TracksSearchResponse) {
            response.results.map { it.map() }
        } else {
            throw Exception(response.errorMessage ?: "Unexpected response type from NetworkClient")
        }
    }

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return dao.getFavoriteTracks().map { tracks ->
            tracks.map { it.toTrack() }
        }
    }

    override suspend fun updateTrackFavoriteStatus(
        track: Track,
        isFavorite: Boolean
    ) {
        dao.updateTrackFavoriteStatus(track.trackId, isFavorite)
    }

    override fun getTrackById(trackId: Long): Flow<Track?> {
        return dao.getTrackById(trackId).map { it?.toTrack() }
    }

    override suspend fun insertTrack(track: Track) {
        dao.insertTrack(track.toEntity())
    }
}
