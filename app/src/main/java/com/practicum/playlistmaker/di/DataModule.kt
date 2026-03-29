package com.practicum.playlistmaker.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.practicum.playlistmaker.data.database.AppDatabase
import com.practicum.playlistmaker.data.network.ITunesApiService
import com.practicum.playlistmaker.data.network.RetrofitNetworkClient
import com.practicum.playlistmaker.data.preferences.SearchHistoryPreferences
import com.practicum.playlistmaker.data.repository.PlaylistsRepositoryImpl
import com.practicum.playlistmaker.data.repository.SearchHistoryRepositoryImpl
import com.practicum.playlistmaker.data.repository.TracksRepositoryImpl
import com.practicum.playlistmaker.domain.api.NetworkClient
import com.practicum.playlistmaker.domain.api.PlaylistsRepository
import com.practicum.playlistmaker.domain.api.SearchHistoryRepository
import com.practicum.playlistmaker.domain.api.TracksRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val Context.dataStore by preferencesDataStore(name = "search_history")

val dataModule = module {

    single { androidContext().dataStore }

    single { SearchHistoryPreferences(get()) }

    single<ITunesApiService> {
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ITunesApiService::class.java)
    }

    single<NetworkClient> {
        RetrofitNetworkClient(get())
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "playlists_maker"
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }

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
