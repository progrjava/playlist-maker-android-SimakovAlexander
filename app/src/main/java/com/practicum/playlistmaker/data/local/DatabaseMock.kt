package com.practicum.playlistmaker.data.local

import com.practicum.playlistmaker.domain.model.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DatabaseMock(val scope: CoroutineScope) {
    private val historyList = mutableListOf<Word>()

    private val _historyUpdates = MutableSharedFlow<Unit>()

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
}