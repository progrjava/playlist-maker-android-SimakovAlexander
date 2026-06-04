package com.practicum.playlistmaker.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.practicum.playlistmaker.data.database.dao.PlaylistTrackDao
import com.practicum.playlistmaker.data.database.dao.PlaylistsDao
import com.practicum.playlistmaker.data.database.dao.TracksDao
import com.practicum.playlistmaker.data.database.entity.PlaylistEntity
import com.practicum.playlistmaker.data.database.entity.PlaylistTrackCrossRef
import com.practicum.playlistmaker.data.database.entity.TrackEntity

@Database(
    entities = [
        TrackEntity::class,
        PlaylistEntity::class,
        PlaylistTrackCrossRef::class
               ],
    version = 6,
    exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun TracksDao(): TracksDao
    abstract fun PlaylistsDao(): PlaylistsDao
    abstract fun PlaylistTrackDao(): PlaylistTrackDao
}