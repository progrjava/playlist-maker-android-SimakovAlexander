package com.practicum.playlistmaker.data.repository

import com.practicum.playlistmaker.data.preferences.SearchHistoryPreferences
import com.practicum.playlistmaker.domain.api.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow

class SearchHistoryRepositoryImpl(
    private val searchHistoryPreferences: SearchHistoryPreferences
) : SearchHistoryRepository {

    override fun getHistoryRequests(): Flow<List<String>> {
        return searchHistoryPreferences.getEntries()
    }

    override suspend fun addToHistory(word: String) {
        searchHistoryPreferences.addEntry(word)
    }
}
