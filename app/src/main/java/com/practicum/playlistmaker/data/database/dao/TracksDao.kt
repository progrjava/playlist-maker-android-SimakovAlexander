package com.practicum.playlistmaker.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practicum.playlistmaker.data.database.entity.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TracksDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrack(track: TrackEntity)

    @Query("SELECT * FROM track_table WHERE favorite = 1 ORDER BY trackName ASC")
    fun getFavoriteTracks(): Flow<List<TrackEntity>>

    @Query("UPDATE track_table SET favorite = :isFavorite WHERE id = :trackId")
    suspend fun updateTrackFavoriteStatus(trackId: Long, isFavorite: Boolean)

    @Query("SELECT * FROM track_table WHERE id = :trackId")
    fun getTrackById(trackId: Long): Flow<TrackEntity?>
}