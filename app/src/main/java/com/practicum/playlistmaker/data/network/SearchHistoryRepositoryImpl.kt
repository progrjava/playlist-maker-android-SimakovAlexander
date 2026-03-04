package com.practicum.playlistmaker.data.network

import com.practicum.playlistmaker.data.local.DatabaseMock
import com.practicum.playlistmaker.data.local.DatabaseProvider
import com.practicum.playlistmaker.domain.model.Word
import com.practicum.playlistmaker.domain.SearchHistoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchHistoryRepositoryImpl(private val scope: CoroutineScope) : SearchHistoryRepository {
    private val database = DatabaseProvider.getDatabase(scope)

    override suspend fun getHistoryRequests(): Flow<List<String>> {
        return database.getHistoryRequests().map { list ->
            list.map { it.word }
        }
    }

    override fun addToHistory(word: String) {
        database.addToHistory(Word(word))
    }
}