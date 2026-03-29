package com.practicum.playlistmaker.domain.api

import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {
    suspend fun addToHistory(word: String)

    fun getHistoryRequests(): Flow<List<String>>
}