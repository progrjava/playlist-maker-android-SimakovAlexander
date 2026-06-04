package com.practicum.playlistmaker.data.database.converter

import com.practicum.playlistmaker.data.database.entity.TrackEntity
import com.practicum.playlistmaker.domain.model.Track

fun TrackEntity.toTrack(): Track {
    return Track(
        trackId = this.id,
        trackName = this.trackName,
        artistName = this.artistName,
        trackTime = this.trackTime,
        image = this.image,
        favorite = this.favorite
    )
}

fun Track.toEntity(): TrackEntity {
    return TrackEntity(
        id = this.trackId,
        trackName = this.trackName,
        artistName = this.artistName,
        trackTime = this.trackTime,
        image = this.image,
        favorite = this.favorite
    )
}


