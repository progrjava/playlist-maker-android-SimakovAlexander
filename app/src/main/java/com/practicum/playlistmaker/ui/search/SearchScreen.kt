package com.practicum.playlistmaker.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.ui.common.CustomTopBar
import com.practicum.playlistmaker.ui.search.components.FailView
import com.practicum.playlistmaker.ui.search.components.InitialStateView
import com.practicum.playlistmaker.ui.search.components.SearchBarCustom
import com.practicum.playlistmaker.ui.search.components.SearchingView
import com.practicum.playlistmaker.ui.search.components.SuccessView
import com.practicum.playlistmaker.ui.search.viewModel.SearchState
import com.practicum.playlistmaker.ui.search.viewModel.SearchViewModel
import com.practicum.playlistmaker.ui.theme.BackgroundPrimary


@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onBackClick: () -> Unit
) {
    val screenState by viewModel.searchScreenState.collectAsState()
    var text by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            CustomTopBar(
                text = stringResource(R.string.search_title),
                onBackClick = onBackClick,
                showBackButton = true
            )
        },
        containerColor = BackgroundPrimary
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .padding(vertical = 8.dp)
                .fillMaxSize()
        ) {
            SearchBarCustom(
                query = text,
                onQueryChange = { text = it; },
                onClearClick = { text = "" },
                onSearchClick = { viewModel.search(text) }
            )

            when (screenState) {
                is SearchState.Initial -> InitialStateView()
                is SearchState.Searching -> SearchingView()
                is SearchState.Success -> SuccessView((screenState as SearchState.Success).list)
                is SearchState.Fail -> FailView((screenState as SearchState.Fail).error)
            }
        }
    }
}