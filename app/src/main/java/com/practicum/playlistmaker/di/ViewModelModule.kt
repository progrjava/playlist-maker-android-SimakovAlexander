package com.practicum.playlistmaker.di

import com.practicum.playlistmaker.ui.playlist.viewModel.PlaylistViewModel
import com.practicum.playlistmaker.ui.playlists.viewModel.PlaylistsViewModel
import com.practicum.playlistmaker.ui.search.viewModel.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        SearchViewModel(get(), get())
    }

    viewModel {
        PlaylistsViewModel(get(), get())
    }

    viewModel { (playlistId: Long) ->
        PlaylistViewModel(get(), playlistId)
    }
}
