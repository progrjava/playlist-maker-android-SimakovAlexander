package com.practicum.playlistmaker.data.network

import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.domain.TracksRepository
import kotlinx.coroutines.delay

class TracksRepositoryImpl() : TracksRepository {
    override suspend fun getAllTracks(): List<Track> {
        delay(1000)// Имитируем запрос к серверу
        return listTracks
    }

    override suspend fun searchTracks(expression: String): List<Track> {
        delay(1000)// Имитируем запрос к серверу
        return listTracks.filter { it.trackName.lowercase().contains(expression.lowercase()) }
    }
}

val listTracks = listOf(
    Track(
        id = 1,
        trackName = "Владивосток 2000",
        artistName = "Мумий Троль",
        trackTime = "2:38",
        image = "",
        favorite = false,
        playlistId = 0
    ),
    Track(
        id = 2,
        trackName = "Группа крови",
        artistName = "Кино",
        trackTime = "4:43",
        image = "",
        favorite = false,
        playlistId = 0
    ),
    Track(
        id = 3,
        trackName = "Не смотри назад",
        artistName = "Ария",
        trackTime = "5:12",
        image = "",
        favorite = false,
        playlistId = 0
    ),
    Track(
        id = 4,
        trackName = "Звезда по имени Солнце",
        artistName = "Кино",
        trackTime = "3:45",
        image = "",
        favorite = false,
        playlistId = 0
    ),
    Track(
        id = 5,
        trackName = "Лондон",
        artistName = "Аквариум",
        trackTime = "4:32",
        image = "",
        favorite = false,
        playlistId = 0
    ),
    Track(
        id = 6,
        trackName = "На заре",
        artistName = "Альянс",
        trackTime = "3:50",
        image = "",
        favorite = false,
        playlistId = 0
    ),
    Track(
        id = 7,
        trackName = "Перемен",
        artistName = "Кино",
        trackTime = "4:56",
        image = "",
        favorite = false,
        playlistId = 0
    ),
    Track(
        id = 8,
        trackName = "Розовый фламинго",
        artistName = "Сплин",
        trackTime = "3:15",
        image = "",
        favorite = false,
        playlistId = 0
    ),
    Track(
        id = 9,
        trackName = "Танцевать",
        artistName = "Мельница",
        trackTime = "3:42",
        image = "",
        favorite = false,
        playlistId = 0
    ),
    Track(
        id = 10,
        trackName = "Чёрный бумер",
        artistName = "Серега",
        trackTime = "4:01",
        image = "",
        favorite = false,
        playlistId = 0
    )
)