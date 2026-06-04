package com.practicum.playlistmaker.ui.search.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.domain.model.Track
import com.practicum.playlistmaker.ui.common.CustomTopBar
import com.practicum.playlistmaker.ui.search.components.FailView
import com.practicum.playlistmaker.ui.search.components.FoundTracksView
import com.practicum.playlistmaker.ui.search.components.HistoryRequests
import com.practicum.playlistmaker.ui.search.components.InitialStateView
import com.practicum.playlistmaker.ui.search.components.NothingFoundView
import com.practicum.playlistmaker.ui.search.components.SearchBarCustom
import com.practicum.playlistmaker.ui.search.components.SearchingView
import com.practicum.playlistmaker.ui.search.viewModel.SearchState
import com.practicum.playlistmaker.ui.search.viewModel.SearchViewModel

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel,
    onTrackClick: (Track) -> Unit,
    onBackClick: () -> Unit
) {
    val screenState by searchViewModel.searchScreenState.collectAsState()
    var historyList by remember { mutableStateOf<List<String>>(emptyList()) }
    val text by searchViewModel.searchQuery.collectAsState()
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current


    LaunchedEffect(text) {
        searchViewModel.updateQuery(text)
    }

    LaunchedEffect(screenState) {
        when (screenState) {
            is SearchState.Success -> {
                focusManager.clearFocus()
            }
            else -> Unit
        }
    }

    LaunchedEffect(Unit) {
        searchViewModel.getHistoryList().collect { list ->
            historyList = list
        }
    }

    Scaffold(
        topBar = {
            CustomTopBar(
                text = stringResource(R.string.search_title),
                onBackClick = onBackClick,
                showBackButton = true
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = MaterialTheme.colorScheme.secondary)
                        .padding(horizontal = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SearchBarCustom(
                        text = text,
                        onValueChange = { searchViewModel.updateQuery(it) },
                        focusRequester = focusRequester,
                        onFocusChanged = { isFocused = it },
                        onClearSearch = {
                            searchViewModel.updateQuery("")
                            searchViewModel.clearSearch()
                        }
                    )

                    if (isFocused && text.isEmpty() && historyList.isNotEmpty()) {
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.outline
                        )
                        HistoryRequests(
                            historyList = historyList,
                            onClick = { word ->
                                searchViewModel.updateQuery(word)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                if (screenState is SearchState.Success.WithTracks) {
                    FoundTracksView(
                        (screenState as SearchState.Success.WithTracks).tracks,
                        onClick = { track ->
                            searchViewModel.onTrackClicked(track)
                            onTrackClick(track)
                        }
                    )
                }
            }

            when (val state = screenState) {
                is SearchState.Initial -> InitialStateView()
                is SearchState.Searching -> SearchingView()
                is SearchState.Success.NothingFound -> NothingFoundView()
                is SearchState.Fail -> {
                    val errorMessage = when (state) {
                        is SearchState.Fail.NetworkError -> state.message
                        is SearchState.Fail.ApiError -> state.message
                        is SearchState.Fail.UnknownError -> state.message
                    }
                    FailView(
                        error = errorMessage,
                        onRefresh = { searchViewModel.retrySearch() }
                    )
                }
                else -> Unit
            }
        }
    }
}
