package com.practicum.playlistmaker.ui.search.viewModel

import com.practicum.playlistmaker.domain.model.Track

sealed interface SearchState {
    object Initial : SearchState
    object Searching : SearchState

    sealed interface Success : SearchState {
        data class WithTracks(val tracks: List<Track>) : Success
        object NothingFound : Success
    }

    sealed interface Fail : SearchState {
        data class NetworkError(val message: String) : Fail
        data class ApiError(val message: String) : Fail
        data class UnknownError(val message: String) : Fail
    }
}
