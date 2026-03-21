package com.practicum.playlistmaker.di

import com.practicum.playlistmaker.ui.playlist.viewModel.PlaylistViewModel
import com.practicum.playlistmaker.ui.playlists.viewModel.PlaylistsViewModel
import com.practicum.playlistmaker.ui.search.viewModel.SearchViewModel
import com.practicum.playlistmaker.ui.settings.viewModel.SettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::SearchViewModel)
    viewModelOf(::PlaylistsViewModel)
    viewModelOf(::SettingsViewModel)

    viewModel { (playlistId: Long) ->
        PlaylistViewModel(get(), playlistId)
    }
}
