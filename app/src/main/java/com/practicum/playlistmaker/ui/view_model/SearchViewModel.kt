package com.practicum.playlistmaker.ui.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {

    var query by mutableStateOf("")
        private set

    fun onQueryChange(newQuery: String) {
        query = newQuery
    }

    fun clearQuery() {
        query = ""
    }
}