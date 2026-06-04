package com.practicum.playlistmaker.data.database.converter


import com.practicum.playlistmaker.data.database.entity.PlaylistEntity
import com.practicum.playlistmaker.domain.model.Playlist

fun PlaylistEntity.toPlaylist(): Playlist {
    return Playlist(
        id = this.playlistId,
        name = this.name,
        description = this.description,
        image = this.imagePath
    )
}

fun Playlist.toEntity(): PlaylistEntity {
    return PlaylistEntity(
        playlistId = this.id,
        name = this.name,
        description = this.description,
        imagePath = this.image
    )
}