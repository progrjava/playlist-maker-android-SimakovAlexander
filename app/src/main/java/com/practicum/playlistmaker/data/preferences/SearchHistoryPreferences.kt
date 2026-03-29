package com.practicum.playlistmaker.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchHistoryPreferences(
    private val dataStore: DataStore<Preferences>,
) {
    private val preferencesKey = stringPreferencesKey("search_history")

    suspend fun addEntry(word: String) {
        if (word.isBlank()) return

        dataStore.edit { preferences ->
            val historyString = preferences[preferencesKey].orEmpty()

            val history = if (historyString.isNotEmpty()) {
                historyString.split(SEPARATOR).toMutableList()
            } else {
                mutableListOf()
            }

            history.remove(word)
            history.add(0, word)

            val subList = if (history.size > MAX_ENTRIES) {
                history.subList(0, MAX_ENTRIES)
            } else {
                history
            }

            preferences[preferencesKey] = subList.joinToString(SEPARATOR)
        }
    }

    fun getEntries(): Flow<List<String>> {
        return dataStore.data.map { preferences ->
            val historyString = preferences[preferencesKey].orEmpty()
            if (historyString.isEmpty()) {
                emptyList()
            } else {
                historyString.split(SEPARATOR)
            }
        }
    }
}

private const val MAX_ENTRIES = 10
private const val SEPARATOR = ","
