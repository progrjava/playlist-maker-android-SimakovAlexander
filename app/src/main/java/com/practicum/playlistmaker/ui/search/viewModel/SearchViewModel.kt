package com.practicum.playlistmaker.ui.search.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.domain.api.SearchHistoryRepository
import com.practicum.playlistmaker.domain.api.TracksRepository
import com.practicum.playlistmaker.domain.model.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val tracksRepository: TracksRepository,
    private val searchHistoryRepository: SearchHistoryRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()
    private val _searchScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val searchScreenState = _searchScreenState.asStateFlow()

    init {
        viewModelScope.launch {
            _searchQuery
                .debounce(1000) // Задержка перед выполнением поиска
                .distinctUntilChanged() // Игнорируем повторяющиеся запросы
                .collect { query ->
                    if (query.isNotEmpty()) {
                        performSearch(query)
                    }
                }
        }
    }

    fun updateQuery(query: String) {
        _searchQuery.value = query
    }

    fun retrySearch() {
        val query = _searchQuery.value
        if (query.isNotEmpty()) {
            performSearch(query)
        }
    }

    private fun performSearch(request: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _searchScreenState.update { SearchState.Searching } // Устанавливаем состояние поиска немедленно

            try {
                val list = tracksRepository.searchTracks(expression = request)
                if (list.isEmpty()) {
                    _searchScreenState.update { SearchState.Success.NothingFound }
                } else {
                    _searchScreenState.update { SearchState.Success.WithTracks(list) }
                    searchHistoryRepository.addToHistory(request) // Добавляем в историю только при успешном поиске
                }
            } catch (e: IOException) {
                _searchScreenState.update { SearchState.Fail.NetworkError("Нет связи. Проверьте подключение к интернету.") }
            } catch (e: HttpException) {
                // Обработка HTTP ошибок от API
                when (e.code()) {
                    400 -> _searchScreenState.update { SearchState.Fail.ApiError("Ошибка запроса: код 400.") }
                    404 -> _searchScreenState.update { SearchState.Fail.ApiError("Ресурс не найден: код 404.") }
                    in 500..599 -> _searchScreenState.update { SearchState.Fail.ApiError("Ошибка сервера: код ${e.code()}.") }
                    else -> _searchScreenState.update { SearchState.Fail.ApiError("Ошибка API: код ${e.code()} - ${e.message()}.") }
                }
            } catch (e: Exception) {
                // Обработка любых других неожиданных исключений
                _searchScreenState.update { SearchState.Fail.UnknownError("Неизвестная ошибка.") }
            }
        }
    }

    fun clearSearch() {
        _searchScreenState.update { SearchState.Initial }
    }

    suspend fun getHistoryList() = searchHistoryRepository.getHistoryRequests()

    fun onTrackClicked(track: Track) {
        viewModelScope.launch {
            if (isExist(track) == null) {
                tracksRepository.insertTrack(track)
            }
        }
    }

    suspend fun isExist(track: Track): Track? {
        return tracksRepository.getTrackByNameAndArtist(track).firstOrNull()
    }
}
