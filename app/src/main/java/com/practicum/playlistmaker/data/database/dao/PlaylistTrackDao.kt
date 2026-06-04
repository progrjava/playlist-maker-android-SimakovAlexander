package com.practicum.playlistmaker.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.practicum.playlistmaker.data.database.entity.PlaylistTrackCrossRef
import com.practicum.playlistmaker.data.database.entity.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistTrackDao {
    @Insert
    suspend fun insertPlaylistTrackCrossRef(crossRef: PlaylistTrackCrossRef)

    @Query("DELETE FROM playlist_track_cross_ref WHERE playlistId = :playlistId AND trackId = :trackId")
    suspend fun deletePlaylistTrackCrossRef(playlistId: Long, trackId: Long)

    @Query("SELECT T.* FROM track_table AS T INNER JOIN playlist_track_cross_ref AS PTC ON T.id = PTC.trackId WHERE PTC.playlistId = :playlistId")
    fun getTracksForPlaylist(playlistId: Long): Flow<List<TrackEntity>>

    @Query("SELECT COUNT(*) FROM playlist_track_cross_ref WHERE playlistId = :playlistId AND trackId = :trackId")
    suspend fun getTrackCountInPlaylist(playlistId: Long, trackId: Long): Int

    @Query("DELETE FROM playlist_track_cross_ref WHERE playlistId = :playlistId")
    suspend fun deleteTracksForPlaylist(playlistId: Long)

    @Query("SELECT COUNT(*) FROM playlist_track_cross_ref WHERE playlistId = :playlistId")
    fun getTracksCountForPlaylist(playlistId: Long): Flow<Int>
}