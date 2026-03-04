package com.practicum.playlistmaker.data.local

import com.practicum.playlistmaker.domain.model.Playlist
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.domain.model.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

object DatabaseProvider {
    private var database: DatabaseMock? = null

    fun getDatabase(scope: CoroutineScope): DatabaseMock {
        if (database == null) {
            database = DatabaseMock(scope)
        }
        return database!!
    }
}

class DatabaseMock(val scope: CoroutineScope) {
    private val historyList = mutableListOf<Word>()
    private val _historyUpdates = MutableSharedFlow<Unit>()
    private val playlists = mutableListOf<Playlist>()
    /*private val tracks = mutableListOf<Track>()*/
    private val tracks = listTracks.toMutableList()

    fun getHistoryRequests(): Flow<List<Word>> = _historyUpdates
        .onStart { emit(Unit) }
        .map { historyList.toList() }

    fun notifyHistoryChanged() {
        scope.launch(Dispatchers.IO) {
            _historyUpdates.emit(Unit)
        }
    }

    fun addToHistory(word: Word) {
        val existingIndex = historyList.indexOfFirst { it.word == word.word }

        if (existingIndex != -1) {
            val existingWord = historyList.removeAt(existingIndex)
            existingWord.count++
            historyList.add(0, existingWord)
        } else {
            historyList.add(0, word)
        }

        notifyHistoryChanged()
    }

    fun getAllPlaylists(): Flow<List<Playlist>> = flow {
        delay(500)
        val filteredPlaylists = mutableListOf<Playlist>()
        playlists.forEach { playlist ->
            val playlistTracks = tracks.filter { it.playlistId == playlist.id }
            filteredPlaylists.add(playlist.copy(tracks = playlistTracks))
        }
        emit(filteredPlaylists.toList())
        delay(100)
    }

    fun getPlayList(id: Long): Flow<Playlist?> = flow {
        emit(playlists.find { it.id == id })
    }

    fun addNewPlaylist(name: String, description: String) {
        playlists.add(
            Playlist(
                id = playlists.size.toLong() + 1,
                name = name,
                description = description,
                tracks = emptyList()
            )
        )
    }

    fun deletePlaylistById(playlistId: Long) {
        playlists.removeIf { it.id == playlistId }
    }

    fun deleteTrackFromPlaylist(trackId: Long) {
        tracks.removeIf { it.trackId == trackId }
    }

    fun getTrackByNameAndArtist(track: Track): Flow<Track?> = flow {
        emit(tracks.find { it.trackName == track.trackName && it.artistName == track.artistName })
    }

    fun insertTrack(track: Track) {
        tracks.removeIf { it.trackId == track.trackId }
        tracks.add(track)
    }

    fun getFavoriteTracks(): Flow<List<Track>> = flow {
        delay(300)
        val favorites = tracks.filter { it.favorite }
        emit(favorites)
    }

    fun deleteTracksByPlaylistId(playlistId: Long) {
        tracks.removeIf { it.playlistId == playlistId }
    }

    fun searchTracks(expression: String): List<Track> {
        return tracks.filter { it.trackName.contains(expression, true) }
    }

    fun getTrackById(trackId: Long): Track? {
        return tracks.find { it.trackId == trackId }
    }
}

val listTracks = listOf(
    Track(
        trackId = 1,
        trackName = "Владивосток 2000",
        artistName = "Мумий Троль",
        trackTime = "2:38",
        image = "",
        favorite = false,
        playlistId = 0
    ),
    Track(
        trackId = 2,
        trackName = "Группа крови",
        artistName = "Кино",
        trackTime = "4:43",
        image = "",
        favorite = false,
        playlistId = 0
    ),
    Track(
        trackId = 3,
        trackName = "Не смотри назад",
        artistName = "Ария",
        trackTime = "5:12",
        image = "",
        favorite = false,
        playlistId = 0
    ),
    Track(
        trackId = 4,
        trackName = "Звезда по имени Солнце",
        artistName = "Кино",
        trackTime = "3:45",
        image = "",
        favorite = false,
        playlistId = 0
    ),
    Track(
        trackId = 5,
        trackName = "Лондон",
        artistName = "Аквариум",
        trackTime = "4:32",
        image = "",
        favorite = false,
        playlistId = 0
    ),
    Track(
        trackId = 6,
        trackName = "На заре",
        artistName = "Альянс",
        trackTime = "3:50",
        image = "",
        favorite = false,
        playlistId = 0
    ),
    Track(
        trackId = 7,
        trackName = "Перемен",
        artistName = "Кино",
        trackTime = "4:56",
        image = "",
        favorite = false,
        playlistId = 0
    ),
    Track(
        trackId = 8,
        trackName = "Розовый фламинго",
        artistName = "Сплин",
        trackTime = "3:15",
        image = "",
        favorite = false,
        playlistId = 0
    ),
    Track(
        trackId = 9,
        trackName = "Танцевать",
        artistName = "Мельница",
        trackTime = "3:42",
        image = "",
        favorite = false,
        playlistId = 0
    ),
    Track(
        trackId = 10,
        trackName = "Чёрный бумер",
        artistName = "Серега",
        trackTime = "4:01",
        image = "",
        favorite = false,
        playlistId = 0
    )
)