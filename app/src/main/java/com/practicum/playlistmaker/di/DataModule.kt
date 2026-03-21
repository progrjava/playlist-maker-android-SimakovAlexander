package com.practicum.playlistmaker.di

import com.practicum.playlistmaker.data.local.DatabaseMock
import com.practicum.playlistmaker.data.network.ITunesApiService
import com.practicum.playlistmaker.data.network.PlaylistsRepositoryImpl
import com.practicum.playlistmaker.data.network.RetrofitNetworkClient
import com.practicum.playlistmaker.data.network.SearchHistoryRepositoryImpl
import com.practicum.playlistmaker.data.network.TracksRepositoryImpl
import com.practicum.playlistmaker.domain.NetworkClient
import com.practicum.playlistmaker.domain.PlaylistsRepository
import com.practicum.playlistmaker.domain.SearchHistoryRepository
import com.practicum.playlistmaker.domain.TracksRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    
    single<ITunesApiService> {
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ITunesApiService::class.java)
    }

    single<NetworkClient> {
        RetrofitNetworkClient(get())
    }

    single { CoroutineScope(SupervisorJob() + Dispatchers.Main) }

    single { DatabaseMock(get()) }

    single<SearchHistoryRepository> {
        SearchHistoryRepositoryImpl(get())
    }

    single<TracksRepository> {
        TracksRepositoryImpl(get(), get())
    }

    single<PlaylistsRepository> {
        PlaylistsRepositoryImpl(get())
    }
}
