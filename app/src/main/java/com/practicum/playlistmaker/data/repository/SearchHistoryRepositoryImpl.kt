package com.practicum.playlistmaker.data.repository

import com.practicum.playlistmaker.data.local.DatabaseMock
import com.practicum.playlistmaker.domain.api.SearchHistoryRepository
import com.practicum.playlistmaker.domain.model.Word
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchHistoryRepositoryImpl(private val database: DatabaseMock) : SearchHistoryRepository {

    override suspend fun getHistoryRequests(): Flow<List<String>> {
        return database.getHistoryRequests().map { list ->
            list.map { it.word }
        }
    }

    override fun addToHistory(word: String) {
        database.addToHistory(Word(word))
    }
}
