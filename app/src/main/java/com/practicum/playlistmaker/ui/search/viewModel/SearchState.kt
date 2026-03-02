package com.practicum.playlistmaker.ui.search.viewModel

import com.practicum.playlistmaker.domain.model.Track

sealed class SearchState {
    object Initial: SearchState()
    object Searching: SearchState()
    sealed class Success : SearchState() {
        data class WithTracks(val tracks: List<Track>) : Success()
        object NothingFound : Success()
    }
    data class Fail(val error: String): SearchState()
}