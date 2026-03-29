package com.practicum.playlistmaker.domain.model

data class Track(
    val trackId: Long,
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val image: String,
    var favorite: Boolean
)
